package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.ImageResponse;
import com.poly.EasyLearning.entity.Lesson;
import com.poly.EasyLearning.exception.LessonException;
import com.poly.EasyLearning.repository.LessonRepo;
import com.poly.EasyLearning.service.ImageStorageService;
import com.poly.EasyLearning.service.LessonService;
import com.poly.EasyLearning.service.QuestionService;
import com.poly.EasyLearning.utils.MessageUtils;
import com.poly.EasyLearning.utils.UploadFolder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {
    private final LessonRepo lessonRepo;
    private final ImageStorageService imageStorageService;
    private final QuestionService questionService;
    private final ImageStorageService storageService;
    @Override
    public List<Lesson> getAllActiveTrue() {
        return lessonRepo.findByActiveTrue();
    }

    @Override
    public List<Lesson> getAllActiveTrueByUser(Integer Id) {
        return lessonRepo.findByUserInfoIdAndActiveTrue(Id);
    }

    @Override
    public Page<Lesson> findAll(Pageable pageable) {
        return lessonRepo.findAll(pageable);
    }

    @Override
    public Lesson create(LessonRequest lessonRequest, AccountApp accountApp) {
        Lesson newLesson = new Lesson();
        BeanUtils.copyProperties(lessonRequest, newLesson);
        newLesson.setUserInfo(accountApp.getUserApp());
        Lesson createdLesson = lessonRepo.save(newLesson);
        createdLesson.getQuestions().forEach(question ->{
            question.setLesson(createdLesson);
        });
//        ImageResponse imageResponse =  imageStorageService
//                .upload(lessonRequest.getImageFile(), UploadFolder.LESSON, String.valueOf(newLesson.getId()));
//        createdLesson.setImage(imageResponse);
        lessonRepo.save(createdLesson);
        log.info("A lesson has been created by user :: " + accountApp.getUsername());
        return newLesson;
    }


    @Override
    public void delete(Integer id) {
        this.lessonRepo.deleteById(id);
    }

    @Override
    public List<Lesson> searchByKeyword(String keyword) {
        return lessonRepo.findByTitleContainingOrDescriptionContainingAndActiveTrue(keyword, keyword);
    }

    @Override
    public Page<Lesson> searchByKeyword(String keyword, Pageable pageable) {
        return lessonRepo.findByTitleContainingOrDescriptionContaining(keyword, keyword, pageable);
    }

    @Override
    public Lesson findById(Integer lessonId) {
        Optional<Lesson> checkLesson = lessonRepo.findById(lessonId);
        if(checkLesson.isEmpty()){
            throw new LessonException(MessageUtils.Lesson.NOT_FOUND.getValue());
        }
        return checkLesson.get();
    }

    @Transactional
    @Override
    public void deleteById(Integer lessonId) {
        Optional<Lesson> checkLesson = lessonRepo.findById(lessonId);
        if (checkLesson.isEmpty()){
            throw new LessonException(MessageUtils.Lesson.NOT_FOUND.getValue());
        }

        if(checkLesson.get().getImage() != null) {
            imageStorageService.delete(checkLesson.get().getImage().getPublicId());
        }

        lessonRepo.delete(checkLesson.get());
        log.info("Lesson with id :: {} has been deleted.", lessonId);
    }

    @Override
    public Lesson updateLesson(Lesson lessonUpdate) {
        Lesson currentLesson = lessonRepo.findById(lessonUpdate.getId()).get();
        if(currentLesson != null){
            currentLesson.setTitle(lessonUpdate.getTitle());
            currentLesson.setDescription(lessonUpdate.getDescription());
            currentLesson.setImage(lessonUpdate.getImage());
        }
        System.out.println(currentLesson.toString());
        return lessonRepo.save(currentLesson);
    }

    @Override
    public Lesson uploadImage(Integer lessonId, MultipartFile imageFile) {
        Optional<Lesson> checkLesson = lessonRepo.findById(lessonId);
        if (checkLesson.isEmpty()){
            throw new LessonException(MessageUtils.Lesson.NOT_FOUND.getValue());
        }
        Lesson lessonUpdate = checkLesson.get();
        ImageResponse imgResponse = storageService
                .upload(imageFile, UploadFolder.LESSON, String.valueOf(lessonId));
        lessonUpdate.setImage(imgResponse);
        return lessonRepo.save(lessonUpdate);
    }

}

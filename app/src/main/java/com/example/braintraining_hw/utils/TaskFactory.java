package com.example.braintraining_hw.utils;

import android.content.Context;
import com.example.braintraining_hw.model.Question;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskFactory {

    public static String writeQuestionsToFile(String filename, Context context) {
        if (FileWork.isFileExists(filename, context)) {
            return "";
        }

        List<String> questions = TestUtils.getListQuestionsAndAnswer();
        return FileWork.writeDataToFile(filename, questions, context);
    }

    public static List<Question> convertStringsToQuestions(List<String> strQuestions) {
        return strQuestions
                .stream()
                .map(TaskFactory::convertStringToQuestion)
                .filter(Optional::isPresent)  // оставляет только те, что содержат Question
                .map(Optional::get)  // извлекает объект Question из Optional
                .collect(Collectors.toList());
    }

    public static Optional<Question> convertStringToQuestion(String str) {
        if (str == null || str.isEmpty()) {
            return Optional.empty();
        }

        String[] splitStr = str.split(AppConstant.SPLIT_QUESTIONS);
        if (splitStr.length != 2) {
            return Optional.empty();
        }

        Question question = new Question(splitStr[0].trim(), splitStr[1].trim());
        return Optional.of(question);
    }
}

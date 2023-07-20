package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// Создание аннотации с параметрами
@Retention(RetentionPolicy.RUNTIME)
@interface SaveTo {
    String path();
}

// Класс, содержащий метод, помеченный аннотацией
class TextContainer {
    private String text;
    public TextContainer(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
    @SaveTo(path = "c://file.txt")
    public void saver() {
        // Пустая реализация метода
    }
}

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // Получение объекта класса TextContainer
        TextContainer obj = new TextContainer("Hello Java!");

        // Получение ссылки на метод с помощью рефлексии
        Method method = TextContainer.class.getMethod("saver");

        // Проверка наличия аннотации SaveTo
        if (method.isAnnotationPresent(SaveTo.class)) {
            // Получение объекта аннотации
            SaveTo annotation = method.getAnnotation(SaveTo.class);

            // Получение значения параметра path
            String filePath = annotation.path();

            method.invoke(obj); // Вызов метода с помощью рефлексии

            // Сохранение текста в файл
            saveToFile(obj.getText(), filePath);

            System.out.println("Text saved to path: " + filePath);
        }
    }

    private static void saveToFile(String text, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

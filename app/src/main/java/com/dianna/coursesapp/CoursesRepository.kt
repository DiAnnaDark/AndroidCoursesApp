package com.dianna.coursesapp

object CoursesRepository {

    fun getCourses(): List<Course> {
        return listOf(
            Course(
                id = 100,
                title = "Java-разработчик с нуля",
                text = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
                price = "999 ₽",
                rate = "4.9",
                startDate = "2024-05-22",
                hasLike = false,
                publishDate = "2024-02-02"
            ),
            Course(
                id = 101,
                title = "3D-дженералист",
                text = "Освой профессию 3D-дженералиста и стань универсальным специалистом, который умеет создавать 3D-модели, текстуры и анимации.",
                price = "12 000 ₽",
                rate = "3.9",
                startDate = "2024-09-10",
                hasLike = false,
                publishDate = "2024-01-20"
            ),
            Course(
                id = 102,
                title = "Python Advanced. Для продвинутых",
                text = "Вы узнаете, как разрабатывать гибкие и высокопроизводительные серверные приложения.",
                price = "1 299 ₽",
                rate = "4.3",
                startDate = "2024-10-12",
                hasLike = true,
                publishDate = "2024-08-10"
            )
        )
    }

    fun getFavoriteCourses(): List<Course> {
        return getCourses().filter { it.hasLike }
    }
}
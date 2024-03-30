package com.ligh.blog.hilt

import dagger.Component

@Component
interface CommonComponent {
    fun student(): Student
}
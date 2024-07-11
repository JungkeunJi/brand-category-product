package com.mss

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class BrandCategoryProductApplication

fun main(args: Array<String>) {
    runApplication<BrandCategoryProductApplication>(*args)
}

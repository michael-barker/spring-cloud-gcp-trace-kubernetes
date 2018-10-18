package io.mbarker.springcloudgcptracekubernetes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SpringCloudGcpTraceKubernetesApplication

@RestController
class FooController {
    @GetMapping
    fun getFoo() = "foo"
}

fun main(args: Array<String>) {
    runApplication<SpringCloudGcpTraceKubernetesApplication>(*args)
}

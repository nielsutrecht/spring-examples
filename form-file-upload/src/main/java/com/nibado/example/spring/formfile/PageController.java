package com.nibado.example.spring.formfile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class PageController {
    @GetMapping
    public ModelAndView index() {
        var dates = IntStream.of(0, 1, 2, 3)
            .mapToObj(i -> LocalDate.now().plusDays(i))
            .toList();

        return new ModelAndView("index", Map.of("dates", dates));
    }

    @PostMapping("submit")
    public ModelAndView submit(FormDataDto formData,
                              @RequestPart(value = "file", required = false) MultipartFile file) {

        var model = Map.of(
            "date", formData.date,
            "number", formData.number,
            "empty", file == null || file.isEmpty(),
            "filename", file == null ? "" : file.getOriginalFilename(),
            "filesize", file == null ? "" : file.getSize());

        return new ModelAndView("submit", model);
    }

    private record FormDataDto(LocalDate date, Integer number) {}
}

package com.example.taskManager.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum statusTask {
    OPEN("Open"),
    CLOSE("Close");

    private String desc;
}

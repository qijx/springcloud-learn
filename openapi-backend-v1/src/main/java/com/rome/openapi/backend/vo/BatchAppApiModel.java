package com.rome.openapi.backend.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class BatchAppApiModel implements Serializable {
    private static final long serialVersionUID = 6704663271450614319L;

    private String appId;

    private String apiIds;
}

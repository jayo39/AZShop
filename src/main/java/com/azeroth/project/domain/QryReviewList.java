package com.azeroth.project.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QryReviewList extends QryResult {
    @JsonProperty("data")
    List<ReviewDomain> list;
}

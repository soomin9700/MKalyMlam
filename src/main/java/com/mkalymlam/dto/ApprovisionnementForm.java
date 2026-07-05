package com.mkalymlam.dto;

import java.util.ArrayList;
import java.util.List;

public class ApprovisionnementForm {

    private List<DetailApprovisionnementForm> details = new ArrayList<>();

    public List<DetailApprovisionnementForm> getDetails() {
        return details;
    }

    public void setDetails(List<DetailApprovisionnementForm> details) {
        this.details = details;
    }
}

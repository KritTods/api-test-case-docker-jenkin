package com.example.demo.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data

public class SlaHop {

        private int hopNumber;
        private OffsetDateTime slaTarget;
        private String ownerId;
        private String teamId;
        private String teamName;
        private int totalDuration;

        // getters and setters
        public int getHopNumber() { return hopNumber; }
        public void setHopNumber(int hopNumber) { this.hopNumber = hopNumber; }
        public OffsetDateTime getSlaTarget() { return slaTarget; }
        public void setSlaTarget(OffsetDateTime slaTarget) { this.slaTarget = slaTarget; }
        public String getOwnerId() { return ownerId; }
        public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
        public String getTeamId() { return teamId; }
        public void setTeamId(String teamId) { this.teamId = teamId; }
        public String getTeamName() { return teamName; }
        public void setTeamName(String teamName) { this.teamName = teamName; }
        public int getTotalDuration() { return totalDuration; }
        public void setTotalDuration(int totalDuration) { this.totalDuration = totalDuration; }

    // (ต่อด้วย getters และ setters ของ CaseData ทุกตัว)
}


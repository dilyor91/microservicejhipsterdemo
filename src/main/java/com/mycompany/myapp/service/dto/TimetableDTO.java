package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Timetable} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TimetableDTO implements Serializable {

    private Long id;

    @NotNull
    private String titleUz;

    @NotNull
    private String titleRu;

    @NotNull
    private String titleKr;

    @NotNull
    private String contentUz;

    @NotNull
    private String contentRu;

    @NotNull
    private String contentKr;

    @NotNull
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleUz() {
        return titleUz;
    }

    public void setTitleUz(String titleUz) {
        this.titleUz = titleUz;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public String getTitleKr() {
        return titleKr;
    }

    public void setTitleKr(String titleKr) {
        this.titleKr = titleKr;
    }

    public String getContentUz() {
        return contentUz;
    }

    public void setContentUz(String contentUz) {
        this.contentUz = contentUz;
    }

    public String getContentRu() {
        return contentRu;
    }

    public void setContentRu(String contentRu) {
        this.contentRu = contentRu;
    }

    public String getContentKr() {
        return contentKr;
    }

    public void setContentKr(String contentKr) {
        this.contentKr = contentKr;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimetableDTO)) {
            return false;
        }

        TimetableDTO timetableDTO = (TimetableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, timetableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimetableDTO{" +
            "id=" + getId() +
            ", titleUz='" + getTitleUz() + "'" +
            ", titleRu='" + getTitleRu() + "'" +
            ", titleKr='" + getTitleKr() + "'" +
            ", contentUz='" + getContentUz() + "'" +
            ", contentRu='" + getContentRu() + "'" +
            ", contentKr='" + getContentKr() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

package org.softuni.mobilele.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "brands")
@NamedEntityGraph(name = "brandWithModels", attributeNodes = @NamedAttributeNode("models"))
public class BrandEntity extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    private List<ModelEntity> models;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime modified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public List<ModelEntity> getModels() {
        return models;
    }

    public void setModels(List<ModelEntity> models) {
        this.models = models;
    }
}

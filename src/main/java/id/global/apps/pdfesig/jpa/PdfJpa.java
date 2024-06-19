package id.global.apps.pdfesig.jpa;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pdf")
public class PdfJpa extends PanacheEntityBase {

    @Id
    @Column(name = "id", length = 40)
    public UUID id;

    @Column(name = "gid_uuid", length = 40, nullable = false, updatable = false)
    public UUID gidUuid;

    @Column(name = "file_path", length = 255, nullable = false, updatable = false)
    public String filePath;

    @Column(name = "file_name", length = 100, nullable = false, updatable = false)
    public String fileName;

    @CreationTimestamp
    @Column(name = "created_at")
    public OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public OffsetDateTime updatedAt;

}

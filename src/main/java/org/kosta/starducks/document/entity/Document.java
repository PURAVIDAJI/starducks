package org.kosta.starducks.document.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "DOCUMENT")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;

    private String docTitle;
    private String docContent;
    private LocalDateTime docDate;
    private LocalDateTime docUpdateDate;
    private LocalDateTime apvDeadline;

    @Enumerated(EnumType.STRING)
    private DocStatus docStatus;

    @ColumnDefault(value = "false")
    private boolean urgent;

    @ColumnDefault(value = "false")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_code", nullable = false)
    private DocForm docForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_writer_id", nullable = false)
    private Employee docWriter;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<Approval> approval = new ArrayList<>();

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<RefEmployee> refEmployee = new ArrayList<>();
//
//    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
//    private List<AttachedFile> attachedFile = new ArrayList<>();
}

package com.github.jeromp.DocumentManagementSystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.github.jeromp.DocumentManagementSystem.model.Document;

public class DocumentTest {

    String documentTitle = "/";
    String documentPath = "Test Document";

    @Test
    public void getDocumentProperties() {
        Document document1 = new Document(this.documentTitle, this.documentPath);
        assertEquals(document1.getTitle(), this.documentTitle);
        document1.setTitle("Test 2 Document");
        assertNotEquals(document1.getTitle(), this.documentTitle);
    }
}
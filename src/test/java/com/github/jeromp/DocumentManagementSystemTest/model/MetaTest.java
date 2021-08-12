package com.github.jeromp.DocumentManagementSystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.github.jeromp.DocumentManagementSystem.model.Meta;
import java.util.UUID;

public class MetaTest {

    String metaKey = "description";
    String metaValue = "sample description";

    @Test
    public void getMetaProperties() {
        Meta meta1 = new Meta(UUID.randomUUID(), this.metaKey, this.metaValue);
        assertEquals(meta1.getValue(), this.metaValue);
        meta1.setValue("sample description 2");
        assertNotEquals(meta1.getValue(), this.metaValue);
    }
}
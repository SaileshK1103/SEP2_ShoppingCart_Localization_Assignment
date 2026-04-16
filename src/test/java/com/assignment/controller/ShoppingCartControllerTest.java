package com.assignment.controller;

import com.assignment.data.DatabaseService;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;

class ShoppingCartControllerTest {

  @BeforeAll
  static void initJFX() {
    try { Platform.startup(() -> {}); } catch (Exception ignored) {}
  }

  @Test
  void testControllerWithMockito() throws Exception {
    ShoppingCartController controller = new ShoppingCartController();
    setupMocks(controller);

    // 1. Setup Mock DatabaseService
    DatabaseService mockService = mock(DatabaseService.class);
    Map<String, String> fakeLabels = new HashMap<>();
    fakeLabels.put("msg.total", "Total:");
    when(mockService.getLabels(anyString())).thenReturn(fakeLabels);

    // 2. Inject Mock into the PRIVATE FINAL field
    Field serviceField = ShoppingCartController.class.getDeclaredField("dbService");
    serviceField.setAccessible(true);
    serviceField.set(controller, mockService);

    // 3. Trigger initialize() - NO PARAMETERS as per your code
    Method initMethod = controller.getClass().getDeclaredMethod("initialize");
    initMethod.setAccessible(true);
    initMethod.invoke(controller);

    // 4. Test Calculation logic
    setText(controller, "inputPrice", "10.0");
    setText(controller, "inputQuantity", "2");

    Method calcMethod = controller.getClass().getDeclaredMethod("handleCalculate");
    calcMethod.setAccessible(true);
    calcMethod.invoke(controller);

    // 5. Test Language Change branch (Arabic for RTL coverage)
    Method langMethod = controller.getClass().getDeclaredMethod("updateLanguage", String.class);
    langMethod.setAccessible(true);
    langMethod.invoke(controller, "ar");

    // Verify that the mock was actually used
    verify(mockService, atLeastOnce()).getLabels(anyString());
  }

  private void setupMocks(ShoppingCartController ctrl) throws Exception {
    inject(ctrl, "rootContainer", new VBox());
    inject(ctrl, "languageSelector", new ComboBox<String>());
    inject(ctrl, "inputPrice", new TextField());
    inject(ctrl, "inputQuantity", new TextField());
    inject(ctrl, "labelPrice", new Label());
    inject(ctrl, "labelQuantity", new Label());
    inject(ctrl, "labelTotalText", new Label());
    inject(ctrl, "labelTotalAmount", new Label());
    inject(ctrl, "btnCalculate", new Button());
  }

  private void inject(Object obj, String fieldName, Object val) throws Exception {
    Field f = obj.getClass().getDeclaredField(fieldName);
    f.setAccessible(true);
    f.set(obj, val);
  }

  private void setText(Object obj, String fieldName, String val) throws Exception {
    Field f = obj.getClass().getDeclaredField(fieldName);
    f.setAccessible(true);
    ((TextField) f.get(obj)).setText(val);
  }
}
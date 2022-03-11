package Risk.view;

import static org.junit.Assert.assertEquals;

public class MouseListenerTest {
/* we don't need to gui test(yet?)
	@Test
	public void testMousePressedNoGUIComponent() {
		ClickListener mouseListener = new ClickListener();
		MouseEvent eventMock = EasyMock.strictMock(MouseEvent.class);
		EasyMock.expect(eventMock.getX()).andReturn(30);
		EasyMock.expect(eventMock.getY()).andReturn(500);
		EasyMock.replay(eventMock);
		mouseListener.mousePressed(eventMock);
		EasyMock.verify(eventMock);

		Point point = mouseListener.getLastClick();
		assertEquals(30, point.x);
		assertEquals(500, point.y);
	}

	@Test
	public void testMousePressedWithGUIComponent() {
		ClickListener mouseListener = new ClickListener();
		MouseEvent eventMock = EasyMock.strictMock(MouseEvent.class);
		GuiComponent componentMock = EasyMock.strictMock(GuiComponent.class);
		EasyMock.expect(eventMock.getX()).andReturn(30);
		EasyMock.expect(eventMock.getY()).andReturn(500);
		componentMock.repaint();
		EasyMock.replay(eventMock, componentMock);
		mouseListener.addComponent(componentMock);
		mouseListener.mousePressed(eventMock);
		EasyMock.verify(eventMock, componentMock);

		Point point = mouseListener.getLastClick();
		assertEquals(30, point.x);
		assertEquals(500, point.y);
	}
	*/
}
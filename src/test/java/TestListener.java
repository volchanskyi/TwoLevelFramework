import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.automationpractice.appmanager.ApplicationManager;

import ru.yandex.qatools.allure.annotations.Attachment;



public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onTestSuccess(ITestResult result) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onTestFailure(ITestResult result) {
	// TODO Auto-generated method stub
	ApplicationManager app = (ApplicationManager) result.getTestContext().getAttribute("app");
	saveScreenshot(app.takeScreenshot());
    }
    
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
	return screenShot;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onStart(ITestContext context) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onFinish(ITestContext context) {
	// TODO Auto-generated method stub
	
    }

}

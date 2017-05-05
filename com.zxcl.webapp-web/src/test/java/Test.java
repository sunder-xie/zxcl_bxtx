

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zxcl.webapp.biz.service.MessageSendProvideService;

import bxtx.intf.weixin.biz.service.WeiXinService;
import bxtx.intf.weixin.dto.SendTemplateMessageDTO;

public class Test {

	@org.junit.Test
	public void testSend(){
		List<SendTemplateMessageDTO> messages = new ArrayList<>();
		SendTemplateMessageDTO a = new SendTemplateMessageDTO();
		a.setToUser("opQP7vuyU36JrIN5BggbzPEkRCw0");
		a.setValue(new String[]{"亲爱的XX","15项","待办事项","2016-01-01 00:00:00","请您尽快处理！"});
		messages.add(a);
		
		ClassPathXmlApplicationContext application = new ClassPathXmlApplicationContext("classpath:/app-biz-intf-ancheng.xml");
		MessageSendProvideService provideService = application.getBean(MessageSendProvideService.class);
		//weiXinService.sendMessageByTemplate("BXTX_MQ_2USER", messages);
		//System.out.println("accesstoken:"+weiXinService.getAccessToken()+"");
		provideService.sendMessage(MessageSendProvideService.CASH_WITHDRAWAL_AUDIT, new String[]{"xiaoming","1","2","3","4","5"});
	}
}

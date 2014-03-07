package edu.columbia.dao;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.ListSubscriptionsByTopicResult;
import com.amazonaws.services.sns.model.ListTopicsResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.Subscription;
import com.amazonaws.services.sns.model.Topic;

public class SnsManager {
	AmazonSNS sns;
	
	public SnsManager()
	{
		this.sns = new AmazonSNSClient(new ClasspathPropertiesFileCredentialsProvider());
	}
	
	public void createTopic(String groupID)
	{
		CreateTopicResult r = sns.createTopic("TwittTubeTopic" + groupID);
	}
	
	public boolean isTopicExisting(String groupID)
	{
		ListTopicsResult r = sns.listTopics();
		
		for (Topic t : r.getTopics())
		{
			String arn = t.getTopicArn();
			String[] arnArr = arn.split(":");
			if (arnArr[arnArr.length - 1].equalsIgnoreCase("TwittTubeTopic" + groupID))
				return true;
		}
		
		return false;
	}
	
	public void subscribe(String groupID, String phoneNumber, String email)
	{
		ListTopicsResult r = sns.listTopics();
		
		for (Topic t : r.getTopics())
		{
			String arn = t.getTopicArn();
			String[] arnArr = arn.split(":");
			if (arnArr[arnArr.length - 1].equalsIgnoreCase("TwittTubeTopic" + groupID))
			{
				sns.subscribe(arn, "sms", phoneNumber);
				sns.subscribe(arn, "email", email);
			}
		}
	}
	
	public void sendMessage(String groupID)
	{
		ListTopicsResult r = sns.listTopics();
		
		for (Topic t : r.getTopics())
		{
			String arn = t.getTopicArn();
			String[] arnArr = arn.split(":");
			if (arnArr[arnArr.length - 1].equalsIgnoreCase("TwittTubeTopic" + groupID))
				sns.publish(arn, "a new video posted", "a new video posted");
		}
	}
}

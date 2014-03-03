import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.columbia.dao.VideosDao;
import edu.columbia.vo.User;
import edu.columbia.vo.Video;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class DaoTests {

	@Autowired
	private  VideosDao videoDao = null;
	
	@Test
	public final void fetchIIPollYears(){
		try {
			List<Video> list = videoDao.getVideosforUser("1001");
			System.out.println("IIPOLL Year List :  --- "+list.toString());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

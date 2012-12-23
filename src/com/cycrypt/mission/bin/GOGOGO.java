package com.cycrypt.mission.bin;

import org.apache.log4j.Logger;

import com.cycrypt.mission.CrackerMissionController;
import com.cycrypt.mission.DoneCallBack;
import com.cycrypt.mission.MHIDMaker;

public class GOGOGO {
	public static void main(String[] args)
	{
		/*System.out.println(FilePathUtil.getAppPath(com.cycrypt.mission.bin.GOGOGO.class));
		if(true)return;*/
		final Logger log =  Logger.getLogger(GOGOGO.class);
		MHIDMaker idMaker = new MHIDMaker();//new MHIDMaker(2001000138, 3);
		CrackerMissionController controller = new CrackerMissionController(idMaker, new DoneCallBack() {
			
			@Override
			public void done() {
				// TODO Auto-generated method stub
				log.info("All missions done, checkout~!");
				System.out.println("All missions done, checkout~!");
			}
		});
		controller.runMissions();
	}
}

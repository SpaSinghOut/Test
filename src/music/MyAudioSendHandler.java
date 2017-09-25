package music;

import net.dv8tion.jda.core.audio.AudioSendHandler;

public class MyAudioSendHandler implements AudioSendHandler{

	@Override
	public boolean canProvide() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] provide20MsAudio() {
		// TODO Auto-generated method stub
		return null;
	}

}

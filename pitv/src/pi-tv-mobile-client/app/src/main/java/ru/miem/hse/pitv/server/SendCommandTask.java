package ru.miem.hse.pitv.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.miem.hse.pitv.common.Configuration;
import ru.miem.hse.pitv.domain.Command;

public class SendCommandTask implements Runnable {

	private static final String TAG = SendCommandTask.class.getName();

	private static final Gson GSON = new GsonBuilder().create();

	private InetSocketAddress serverAddress = new InetSocketAddress(Configuration.Server.ADDRESS,
			Integer.parseInt(Configuration.Server.PORT));

	private SocketChannel clientChannel;

	private String message;

	private Command command;

	public SendCommandTask(Command command) {
		this.command = command;
	}

	@Override
	public void run() {
		try {
			clientChannel = SocketChannel.open(serverAddress);
			Log.i(TAG, "Connecting to server " + serverAddress);
			message = GSON.toJson(command);
			ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));

			clientChannel.write(buffer);

			buffer.clear();
		} catch (IOException e) {
			Log.e(TAG, "SendCommandTask IOException", e);
		} finally {
			try {
				clientChannel.close();
			} catch (IOException e) {
				Log.e(TAG, "Cannot finally close clientChannel", e);
			}
		}
	}
}

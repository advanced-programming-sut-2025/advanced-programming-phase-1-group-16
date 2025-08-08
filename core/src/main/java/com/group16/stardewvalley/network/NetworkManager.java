package com.group16.stardewvalley.network;

import com.group16.stardewvalley.Message;

public interface NetworkManager {
    Message sendAndWait(Message message);
}

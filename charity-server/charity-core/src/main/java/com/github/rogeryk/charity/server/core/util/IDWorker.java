package com.github.rogeryk.charity.server.core.util;


import java.net.InetAddress;
import java.net.UnknownHostException;

public class IDWorker {

    private static final int MACHINE_BIT = 10;

    private static final int TIME_BIT = 40;

    private static final int SEQUENCE_BIT = 13;

    private static final int MACHINE_LEFT = TIME_BIT + SEQUENCE_BIT;

    private static final int TIME_LEFT = SEQUENCE_BIT;

    private static final long MACHINE_MASK = Long.MAX_VALUE;

    private static final long TIME_MASK = (Long.MAX_VALUE >>> MACHINE_BIT);

    private static final long SEQUENCE_MASK = (Long.MAX_VALUE >>> (MACHINE_BIT + TIME_BIT));

    private static final long SEQUENCE_MAX = (1 << SEQUENCE_BIT) - 1;

    private long machineId;

    private long lastTime = 0;

    private long sequence = 0;

    public IDWorker() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        byte[] ipBytes = addr.getAddress();
        machineId |= ipBytes[2];
        machineId <<= 8;
        machineId |= ipBytes[3];
        machineId <<= MACHINE_LEFT;
        machineId &= MACHINE_MASK;
    }


    public synchronized long nextId() {
        while (true) {
            long now = System.currentTimeMillis();
            if (now != lastTime) {
                lastTime = now;
                sequence = 0;
            }
            long curSequence = sequence++;
            if (curSequence > SEQUENCE_MAX) {
                continue;
            }
            return machineId | ((now << TIME_LEFT) & TIME_MASK) | ((curSequence & SEQUENCE_MASK));
        }
    }
}


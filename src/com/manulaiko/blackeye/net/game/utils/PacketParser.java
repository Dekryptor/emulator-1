package com.manulaiko.blackeye.net.game.utils;

/**
 * Packet class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 * @package com.manulaiko.blackeye.net.game
 */
public class PacketParser
{
    /**
     * Packet as array
     */
    private java.util.ArrayList<String> _packet = new java.util.ArrayList<>();

    /**
     * Index pointer
     */
    private int _i = 0;

    /**
     * Constructor
     */
    public PacketParser()
    {
    }

    /**
     * Constructor
     *
     * @param packet Packet
     */
    public PacketParser(String packet)
    {
        String[] p = packet.split("\\|");

        for(String s : p) {
            this._packet.add(s);
        }
    }

    /**
     * Adds a string to the array
     *
     * @param str String to add
     */
    public void add(String str)
    {
        this._packet.add(str);
    }

    /**
     * Adds an integer to the array
     *
     * @param i Integer to add
     */
    public void add(long i)
    {
        this._packet.add(Long.toString(i));
    }

    /**
     * Adds a boolean to the array
     *
     * @param b Boolean to add
     */
    public void add(boolean b)
    {
        if(b) {
            this._packet.add("1");
        } else {
            this._packet.add("0");
        }
    }

    /**
     * Adds a double to the array
     *
     * @param d Double to add
     */
    public void add(double d)
    {
        this._packet.add(Double.toString(d));
    }

    /**
     * Returns next index as String
     *
     * @return Next index as a string
     */
    public String readString()
    {
        return this._packet.get(this._i++);
    }

    /**
     * Returns next index as an integer
     *
     * @return Next index as an integer
     */
    public int readInt()
    {
        return Integer.parseInt(this.readString());
    }

    /**
     * Returns next indexs as a boolean
     *
     * @return Next index as a boolean
     */
    public boolean readBoolean()
    {
        String i = this.readString();

        if(i.equalsIgnoreCase("true") || i.equals("1")) {
            return true;
        }

        return false;
    }

    /**
     * Returns next indexs as a double
     *
     * @return Next index as a double
     */
    public double readDouble()
    {
        return Double.parseDouble(this.readString());
    }

    /**
     * Returns the packet as a string
     */
    public String toString()
    {
        String str = "";

        for(String s : this._packet) {
            if(!str.equalsIgnoreCase("")) {
                str += "|";
            }

            str += s;
        }

        return str;
    }
}

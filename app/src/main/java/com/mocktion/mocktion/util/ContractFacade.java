package com.mocktion.mocktion.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocktion.mocktion.dto.ArticleDto;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by user on 2018/03/10.
 */

public class ContractFacade {

    public static final String TESTNET_HOST = "153.230.176.93";
    public static final int TESTNET_PORT = 8545;
    public static final String CONTRACT_ADDRESS = "0xb079896f6844e9cf110555b4c73933c5d341b2d3";

    private static ObjectMapper objectMapper = new ObjectMapper();

    //addToken(uint256)
    static public void addToken(String sender, long value) {

        Function function = new Function(
            "addToken",
                Arrays.asList(new Uint256(BigInteger.valueOf(value))),
                Collections.<TypeReference<?>>emptyList()
        );
        String resStr = call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );

    }

    //agree()
    static public void agree(String sender) {

        Function function = new Function(
                "agree",
                Collections.emptyList(),
                Collections.emptyList()
        );

        call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );

    }

    //bidPrice(address,uint) returns (bool)
    static public boolean bidPrice(String sender, String address, long bidPrice) {

        Function function = new Function(
                "bidPrice",
                Arrays.asList(
                        new Address(address),
                        new Uint(BigInteger.valueOf(bidPrice))
                ),
                Collections.singletonList(new TypeReference<Bool>() {}
                )
        );

        String resStr = call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );
        List<Type> decoded = FunctionReturnDecoder.decode(
                resStr,
                function.getOutputParameters());
        return (boolean) decoded.get(0).getValue();

    }

    //cancel()
    static public void cancel(String sender) {

        Function function = new Function(
                "cancel",
                Collections.emptyList(),
                Collections.emptyList()
        );

        call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );

    }

    //close()
    static public void close(String sender) {

        Function function = new Function(
                "close",
                Collections.emptyList(),
                Collections.emptyList()
        );

        call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );

    }

    //exhibit(string,uint,bool) returns (uint)
    static public int exhibit(String sender, String name, long basePrice, boolean isRevers) {

        Function function = new Function(
                "exhibit",
                Arrays.asList(
                        new Utf8String(name),
                        new Uint(BigInteger.valueOf(basePrice)),
                        new Bool(isRevers)
                ),
                Collections.singletonList(
                        new TypeReference<Uint>() {}
                )
        );

        String resStr = call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );
        List<Type> decoded = FunctionReturnDecoder.decode(
                resStr,
                function.getOutputParameters());
        return (int) decoded.get(0).getValue();

    }

    //getArticle(address) returns (uint,string,uint,uint,bool,address)
    static public ArticleDto getArticle(String sender, String address) {

        Function function = new Function(
                "getArticle",
                Arrays.asList(
                        new Address(address)
                ),
                Arrays.asList(
                        new TypeReference<Uint>() { },
                        new TypeReference<Utf8String>() { },
                        new TypeReference<Uint>() { },
                        new TypeReference<Uint>() { },
                        new TypeReference<Bool>() { },
                        new TypeReference<Address>() { }
                )
        );

        String resStr = call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );
        List<Type> decoded = FunctionReturnDecoder.decode(
                resStr,
                function.getOutputParameters());

        ArticleDto dto = new ArticleDto();
        dto.id=(int) decoded.get(0).getValue();
        dto.name=(String) decoded.get(1).getValue();
        dto.basePrice=(int) decoded.get(2).getValue();
        dto.lastPrice=(int) decoded.get(3).getValue();
        dto.isReverse=(boolean) decoded.get(4).getValue();
        dto.lastBidder=(String) decoded.get(5).getValue();

        return dto;
    }

    //getBalance() return(uint)
    static public String getBalance(String sender) {

        Function function = new Function(
                "getBalance",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint>() {}
                )
        );

        String resStr = call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );
        List<Type> decoded = FunctionReturnDecoder.decode(
                resStr,
                function.getOutputParameters());
        return decoded.get(0).getValue().toString();

    }

    //getHistory(uint) returns (uint,string,uint,uint,bool,address)
    static public ArticleDto getHistory(String sender, int id) {

        Function function = new Function(
                "getHistory",
                Arrays.asList(
                        new Uint(BigInteger.valueOf(id))
                ),
                Arrays.asList(
                        new TypeReference<Uint>() { },
                        new TypeReference<Utf8String>() { },
                        new TypeReference<Uint>() { },
                        new TypeReference<Uint>() { },
                        new TypeReference<Bool>() { },
                        new TypeReference<Address>() { }
                )
        );

        String resStr = call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );
        List<Type> decoded = FunctionReturnDecoder.decode(
                resStr,
                function.getOutputParameters());

        ArticleDto dto = new ArticleDto();
        dto.id=(int) decoded.get(0).getValue();
        dto.name=(String) decoded.get(1).getValue();
        dto.basePrice=(int) decoded.get(2).getValue();
        dto.lastPrice=(int) decoded.get(3).getValue();
        dto.isReverse=(boolean) decoded.get(4).getValue();
        dto.lastBidder=(String) decoded.get(5).getValue();

        return dto;
    }

    //getHlist() returns (uint[])
    static public String getHlist(String sender) {

        Function function = new Function(
                "getHlist",
                Collections.emptyList(),
                Collections.singletonList(
                        new TypeReference<Utf8String>() {}
                )
        );

        String resStr = call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );
        List<Type> decoded = FunctionReturnDecoder.decode(
                resStr,
                function.getOutputParameters());
        return (String) decoded.get(0).getValue();

    }

    //getSeconds() returns (unit)
    static public int getSeconds(String sender) {

        Function function = new Function(
                "getHlist",
                Collections.emptyList(),
                Collections.singletonList(
                        new TypeReference<Uint>() {}
                )
        );

        String resStr = call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );
        List<Type> decoded = FunctionReturnDecoder.decode(
                resStr,
                function.getOutputParameters());
        return (int) decoded.get(0).getValue();
    }

    //getVersion() return (string)
    static public String getVersion(String sender) {

        Function function = new Function(
                "getHlist",
                Collections.emptyList(),
                Collections.singletonList(
                        new TypeReference<Utf8String>() {}
                )
        );

        String resStr = call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );
        List<Type> decoded = FunctionReturnDecoder.decode(
                resStr,
                function.getOutputParameters());
        return (String) decoded.get(0).getValue();
    }

    //withdrawal(uint)
    static public void withdrawal(String sender, int amount) {

        Function function = new Function(
                "withdrawal",
                Arrays.asList(
                        new Uint(BigInteger.valueOf(amount))
                ),
                Collections.emptyList()
        );

        call(
                sender,
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(function)
        );

    }

    static private String call(String from, String to, String data) {

        final String json =
                "{\"jsonrpc\":\"2.0\"," +
                        "\"id\":200603988," +
                        "\"method\":\"eth_call\"," +
                        "\"params\":[" +
                        "{" +
//                                "\"from\":\"0x48d0c396e8f0b1f1fe57268974db211dd3392dfa\"," +
//                                "\"to\":\"0x9273ca13535c987ddeed3cfe3654ce5360f1b1b0\"," +
//                                "\"data\":\"0xb49f4afd" +
                        "\"from\":\"" + from + "\"," +
                        "\"to\":\"" + to + "\"," +
                        "\"data\":\"" + data +
                        "\"}," +
                        "\"pending\"]" +
                        "}";

        Object result = null;

        try {

            String buffer = "";
            HttpURLConnection con = null;
            String protocol = "http";
            String host = TESTNET_HOST;
            int port = TESTNET_PORT;
            String filePath = "";
            URL url = new URL(protocol, host, port, filePath);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setInstanceFollowRedirects(false);
            con.setRequestProperty("Accept-Language", "jp");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            OutputStream os = con.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.print(json);
            ps.close();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            buffer = reader.readLine();

            Object resultObj = objectMapper.readValue(buffer.toString(), Object.class);
            LinkedHashMap<String, Object> resultMap = (LinkedHashMap<String, Object>) resultObj;
            result = resultMap.get("result");

            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

}

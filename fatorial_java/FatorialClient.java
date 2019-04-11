import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import javax.sound.midi.Soundbank;



import java.util.Scanner;
public class FatorialClient {

	public static void main(String[] args) throws IOException {
		/**if	(args.length != 2) {
			System.out.println("Uso: java FatorialClient <maquina> <numero>");
			System.exit(1);
		}
		*/
		//CLIENTE INFORMA A MENSAGEM A SER ENVIADA
		System.out.println("Digite a mensagem a ser enviada ao servidor: ");
		Scanner sc = new Scanner(System.in);
		//ARMAZENANDO A MENSAGEM INFORMADA
		String mensagem = sc.nextLine();
		
		// CRIA UM DATAGRAM SOCKET PARA CONEXÃO COM O SERVER
		DatagramSocket socket = new DatagramSocket();
		// BUFFER DE ENVIO
		byte[] mensagemEnviada = new byte[1024];
		//CONVERTENDO MENSAGEM EM UM ARRAY DE BYTES
		mensagemEnviada = mensagem.getBytes();
		//INFORMANDO O IP DO SERVIDOR
		InetAddress serv_ip = InetAddress.getByName("localhost");
		DatagramPacket output = new DatagramPacket(mensagemEnviada, mensagemEnviada.length, serv_ip, 30000);
		socket.send(output);
		
		// obtem a resposta
		byte[] fat = new byte[1024];
		try {
			DatagramPacket input = new DatagramPacket(fat, fat.length);
			socket.setSoTimeout(1000);
			socket.receive(input);
			// mostra a resposta
			
			System.out.println("RESPOSTA RECEBIDA: "+traduzResposta(input));
		
			
			/**System.out.println("Fatorial de " + args[1] + " = " + ByteBuffer.wrap(fat).getInt());*/
		} catch	(IOException e) {
			System.out.println("Servidor offline.");
		}
		
		// fecha o socket
		socket.close();
	}
	
	private static String traduzResposta(DatagramPacket pacote) {
		
		//TRADUZINDO DADOS DO PACOTE
		String respostasTratadas = new String(pacote.getData());
		//CRIA ARRAY DE STRING PARA PEGAR DADOS JUNTOS E SEPARA-LOS ATRAVÉS DO SPLIT
		String[] dadosSeparados = new String[2];
		respostasTratadas.split(";"); 
		
		String tamanhoString = dadosSeparados[1].toString();
		String totalPalavras = dadosSeparados[2].toString();
		
		//MENSAGEM TRADUZIDA
		String respostaServidor = "TAMANHO DA STRING:  "+tamanhoString +" "+"TOTAL DE PALAVRAS:  "+totalPalavras;
	return respostaServidor;
		
	}

}

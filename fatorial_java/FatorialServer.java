import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * 	4-Modifique a funcionalidade do programa do item 1 de tal forma que o cliente
 *	envie uma string e o servidor, ao receber essa, retorne o tamanho da string em
 *	bytes e o número de palavras contidas nessa string ao cliente.
 *
 * @author Evandro Rocha da Cunha e Miguel Luciano Benites da Silva
 *
 */
public class FatorialServer {
	private static int calculaFatorial(int num) {
		int fat = 1;

		if (num < 0) return -1;

		for (int i = 0; i < num; i++)
			fat *= (num - i);

		return fat;
	}
	
	// DESCOMPACTA O PACOTE E RETORNA DADOS JÁ TRATADOS CONVERTIDOS EM UMA ARRAY DE BYTES EM RESPOSTA
	
	private static byte[] criarResposta(DatagramPacket pacote) {
		
		//PEGA DADOS E TRADUZ PARA STRING
		String dados = new String(pacote.getData());
		//PEGA O TAMANHO DA STRING A SER ENVIADA
		int tamanho = dados.length();
		//SEPARANDO AS PALAVRAS PARA SER CONTADAS
		String[] listaDePalavras = dados.split(" ");
		//PEGA O TOTAL DE PALAVRAS
		int totalPalavras = listaDePalavras.length;
		
		//CONVERTE DADOS RECEBIDOS PARA STRING
		
		String dadosrecebidos = ""+tamanho +";"+""+totalPalavras;
		//CRIA A ARRAY DE BYTES A SER ENVIADO
		byte[] respostas = new byte[1024];
		//ARRAY DE BYTES RECEBE DADOS JÁ CONVERTIDOS EM BYTE
		respostas = dadosrecebidos.getBytes();
		
		return respostas;
		
	}

	public static void main(String[] args) throws IOException {
		DatagramSocket socket	= new DatagramSocket(30000);
		byte[] buff_in	= new byte[1024];
		byte[] buff_out	= new byte[1024];

		while (true) {
			try {
				DatagramPacket input = new DatagramPacket(buff_in, buff_in.length);
				socket.receive(input);
				
				//PEGA O IP DO CLIENT
				InetAddress resp_ip = input.getAddress();
				//PEGA A PORTA DO CLIENT PARA RESPOSTA
				int resp_port = input.getPort();
				//CRIA A ARRAY COM A RESPOSTA
				buff_out = criarResposta(input);
				//ENVIA A MENSAGEM AO CLIENT
				DatagramPacket output = new DatagramPacket(buff_out, buff_out.length, resp_ip, resp_port);

				//String message = new String(input.getData(), 0, input.getLength());

				//int fatorial = calculaFatorial(Integer.parseInt(message));
				//ByteBuffer.wrap(buff_out).putInt(fatorial);
		
				
				socket.send(output);
			} catch	(IOException e) {
				e.printStackTrace();
				break;
			}
		}
		socket.close();
	}

}

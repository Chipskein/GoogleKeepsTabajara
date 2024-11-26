package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import negocio.Anotacao;
import negocio.Cores;


public class AnotacaoDAO {

        public ArrayList<Anotacao> listar(String orderby) throws SQLException{
                if (orderby == null || !orderby.equals("asc") && !orderby.equals("desc")){
                        orderby = "asc";
                }
                ArrayList<Anotacao> anotacoes = new ArrayList<>();
                String sql = "SELECT id,titulo,data_hora,descricao,cor_ansi,foto FROM anotacao ORDER BY data_hora "+orderby+";";
                Connection conexao = new ConexaoPostgreSQL().getConexao();
                PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()){
                        Anotacao a = new Anotacao(
                                rs.getInt("id"),
                                rs.getString("titulo"),
                                rs.getString("descricao"),
                                rs.getTimestamp("data_hora").toLocalDateTime(),
                                Cores.fromString(rs.getString("cor_ansi")),
                                rs.getBytes("foto")
                        );
                        anotacoes.add(a);
                }
                conexao.close();
                return anotacoes;
        }

        public Anotacao inserir(Anotacao a) throws SQLException{
                String sql = "INSERT INTO anotacao(titulo,data_hora,descricao,cor_ansi,foto) VALUES (?,?,?,?,?)";
                Connection conexao = new ConexaoPostgreSQL().getConexao();
                String generatedColumns[] = { "id" };
                PreparedStatement preparedStatement = conexao.prepareStatement(sql,generatedColumns);
                preparedStatement.setString(1,a.getTitulo());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(a.getData_hora()));
                preparedStatement.setString(3,a.getDescricao());
                preparedStatement.setString(4,a.getCor().name());
                preparedStatement.setBytes(5,a.getFoto());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next()){
                        int last_inserted_id = rs.getInt(1);
                        a.setId(last_inserted_id);
                }
                conexao.close();
                return a;
        };

        public void deletar(int id) throws SQLException{
                String sql = "DELETE FROM anotacao a where a.id=?";
                Connection conexao = new ConexaoPostgreSQL().getConexao();
                PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setInt(1,id);
                preparedStatement.executeUpdate();
                conexao.close();
        };
        
        public Anotacao atualizar(Anotacao a) throws SQLException{
                String sql = "UPDATE  anotacao SET titulo = ?, data_hora = ?, descricao = ?, cor_ansi = ?, foto = ? WHERE id = ?";
                Connection conexao = new ConexaoPostgreSQL().getConexao();
                PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setString(1,a.getTitulo());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(a.getData_hora()));
                preparedStatement.setString(3,a.getDescricao());
                preparedStatement.setString(4,a.getCor().name());
                preparedStatement.setBytes(5,a.getFoto());
                preparedStatement.setInt(6,a.getId());
                preparedStatement.executeUpdate();

                sql = "SELECT * FROM anotacao WHERE id = ?";
                PreparedStatement ps = conexao.prepareStatement(sql);
                ps.setInt(1,a.getId());
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                        a.setId(rs.getInt("id"));
                        a.setTitulo(rs.getString("titulo"));
                        a.setData_hora(rs.getTimestamp("data_hora").toLocalDateTime());
                        a.setDescricao(rs.getString("descricao"));
                        a.setCor(Cores.fromString(rs.getString("cor_ansi")));
                        if (rs.getString("foto")!=null){
                                a.setFoto(rs.getString("foto").getBytes());
                        }
                }
                ps.executeQuery();
                conexao.close();
                return a;
        };

        public Anotacao pegarPorId(int id) throws SQLException{
                String sql = "SELECT * FROM anotacao WHERE id = ?";
                Connection conexao = new ConexaoPostgreSQL().getConexao();
                PreparedStatement ps = conexao.prepareStatement(sql);
                ps.setInt(1,id);
                ResultSet rs = ps.executeQuery();
                Anotacao a=null; 
                if(rs.next()){
                        a = new Anotacao(
                                rs.getInt("id"),
                                rs.getString("titulo"),
                                rs.getString("descricao"),
                                rs.getTimestamp("data_hora").toLocalDateTime(),
                                Cores.fromString(rs.getString("cor_ansi")),
                                rs.getString("foto")!=null ? rs.getString("foto").getBytes() : null    
                        );
                }
                ps.executeQuery();
                conexao.close();
                return a;
        }
}

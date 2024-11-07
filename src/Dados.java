import java.io.*;
import java.util.ArrayList;

public abstract class Dados {
    private static final String arquivoUsuario = "Usuarios.ser";



    public static Usuario logar(String email, String senha) {
        Usuario usuario = obterUsuarioPorEmail(email);

        if (usuario == null) {
            return null;
        }
        if (usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;
    }

    //ADMS
//    public static void salvarAdm(String nome, String email, String senha) {
//        Admin adm = new Admin(obterAdms().size() + 1, nome, email, senha);
//        try (FileOutputStream fileOut = new FileOutputStream(arquivoAdmin, true);
//             ObjectOutputStream out = new ObjectOutputStream(fileOut) {
//                 protected void writeStreamHeader() throws IOException {
//                     if (fileOut.getChannel().position() == 0) {
//                         super.writeStreamHeader();
//                     } else {
//                         reset();
//                     }
//                 }
//             }) {
//            out.writeObject(adm);
//            System.out.println("adm salvo com sucesso: " + adm.getNome());
//        } catch (IOException e) {
//            System.out.println("Erro ao salvar o adm: " + e.getMessage());
//        }
//    }
//
//    private static ArrayList<Admin> obterAdms() {
//        ArrayList<Admin> adms = new ArrayList<>();
//        try (FileInputStream fileIn = new FileInputStream(arquivoAdmin);
//             ObjectInputStream in = new ObjectInputStream(fileIn)) {
//            while (true) {
//                try {
//                    Admin adm = (Admin) in.readObject();
//                    adms.add(adm);
//                } catch (EOFException e) {
//                    break;
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            System.out.println("Erro ao carregar adms: " + e.getMessage());
//        }
//        return adms;
//    }
//
//    private static boolean logarAdm(String email, String senha) {
//        Admin adm = obterAdminPorEmail(email);
//        if (adm == null) {
//            return false;
//        }
//        if (adm.getSenha().equals(senha)) return true;
//        return false;
//
//    }
//
//    public static Admin logaAdm(String email, String senha) {
//        Admin adm = obterAdminPorEmail(email);
//
//        if (adm == null) {
//            return null;
//        }
//        if (adm.getSenha().equals(senha)) return adm;
//        return null;
//    }
//
//    private static Admin obterAdminPorEmail(String email) {
//        ArrayList<Admin> adms = obterAdms();
//        for (Admin adm : adms) {
//            if (adm.getEmail().equals(email)) {
//                return adm;
//            }
//        }
//        return null;
//    }

    // Clientes


    //PRODUTOS

    //ADM

    //ADM


    //ADM

    public static ArrayList<Produto> obterProdutos() {
        ArrayList<Produto> produtos = new ArrayList<>();
        String arquivoProdutos = "produtos.ser";
        try (FileInputStream fileIn = new FileInputStream(arquivoProdutos);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            while (true) {
                try {
                    Produto produto = (Produto) in.readObject();
                    produtos.add(produto);
                } catch (EOFException e) {
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar produtos: " + e.getMessage());
        }
        return produtos;
    }

    public static Produto obterProdutoPorId(int id) {
        ArrayList<Produto> produtos = obterProdutos();
        for (Produto prod : produtos) {
            if (prod.getId() == id) {
                return prod;
            }
        }
        return null;
    }

    //ADM



    public static Produto obterProdutoPorNome(String nome) {
        ArrayList<Produto> produtos = obterProdutos();
        for (Produto prod : produtos) {
            if (prod.getNome().equals(nome)) {
                return prod;
            }
        }
        return null;
    }

    //ADM


    //ADM


    //ADM



    //    CARRINHO

    public static void DeletarItemCarrinho(int idCliente, int idProduto) throws UsuarioInvalido{
        Cliente c;
        c = Dados.obterClientePorId(idCliente);
        try {
            c.getCarrinho().deletaItemCarrinho(Dados.obterProdutoPorId(idProduto));
            atualizaUsuario(idCliente, c);
        }catch (UsuarioInvalido ex){
            throw new UsuarioInvalido("nao foi possivel deletar o item do carrinho");
        }
    }

    //UsuarioCliente
    public static ArrayList<Usuario> obterUsuarios(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(arquivoUsuario);
        ObjectInputStream in = new ObjectInputStream(fileIn)) {
            while (true) {
                try {
                    usuarios.add((Usuario)in.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar clientes: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public static Usuario obterUsuarioPorEmail(String email) {
        ArrayList<Usuario>Clientes = obterUsuarios();
        for (Usuario c : Clientes) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }

    public static Cliente obterClientePorId(int id) {
        ArrayList<Usuario> usuarios;
        usuarios = obterUsuarios();
        for (Usuario c : usuarios) {
            if(c.getId() == id){
                return (Cliente)c;
            }
            break;
        }
        return null;
    }

    public static void cadastraNovoUsuarioCliente(Usuario usuario) {

        ArrayList<Usuario> usuarios = obterUsuarios();
        int id = usuarios.size()+1;

        boolean clienteExiste = false;
        for (Usuario u: usuarios) {
            if (u.getNome().equals(usuario.getNome())) {
                clienteExiste = true;
                break;
            }
        }

        if (!clienteExiste) {
            usuarios.add(usuario);
            try (FileOutputStream fileOut = new FileOutputStream(arquivoUsuario);
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                for (Usuario u1 : usuarios) {
                    out.writeObject(u1);
                }
                System.out.println("Cliente salvo com sucesso: " + usuario.getNome());
            } catch (IOException e) {
                System.out.println("Erro ao salvar o cliente: " + e.getMessage());
            }
        } else {
            System.out.println("cliente já existe: " + usuario.getNome());
        }


    }

    public static void cadastraCliente(String nome,String email,String senha){
        int id = obterUsuarios().size()+1;
        cadastraNovoUsuarioCliente(new Cliente(id,nome,email,senha));
    }

    public static void atualizarUsuarioCliente(int id, Cliente cliente) throws UsuarioInvalido {
        ArrayList<Usuario> clientes = obterUsuarios();
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                clientes.set(i, cliente);
                break;
            }

        }
        try {
        salvaListaUsuarios(clientes);
        }catch (UsuarioInvalido e){
            throw new UsuarioInvalido("Nao foi possivel atualizar o cliente");
        }
    }

    public static void salvaListaUsuarios(ArrayList<Usuario> us) throws UsuarioInvalido{
        try (FileOutputStream fileOut = new FileOutputStream(arquivoUsuario);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            for (Usuario c : us) {
                out.writeObject(c);
            }
            System.out.println("usuarios atualizados com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro no metodo salvaListaUsuarios, ao salvar usuarios: " + e.getMessage());
        }
    }

    public static void atualizaUsuario(int id, Usuario u) throws UsuarioInvalido {
            ArrayList<Usuario> usuarios = obterUsuarios();
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getId() == id) {
                    usuarios.set(i, u);
                    System.out.println("Usuario: " + u.getNome() + " atualizado com sucesso.");
                }
            }
            try {
            salvaListaUsuarios(usuarios);
            }catch (UsuarioInvalido ex ){
                throw new UsuarioInvalido("Erro no metodo atualizaUsuario, ao atualizar o cliente");
            }
    }

    public static void deletaUsuario(String email) throws UsuarioInvalido{
        ArrayList<Usuario> usuarios = obterUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equals(email)) {
                usuarios.remove(i);
            }
        }
        try {
            salvaListaUsuarios(usuarios);
        }catch (UsuarioInvalido ex){
            throw new UsuarioInvalido("Erro ao deletar o cliente");
        }
    }

    public static void mostraCarrinhoUsuarios(){
        for(Usuario u : obterUsuarios()){
            if(u instanceof Cliente c){
                System.out.println(u.getNome()+":");
                c.getCarrinho().imprimeCarrinh();
                }
            }
        }

}


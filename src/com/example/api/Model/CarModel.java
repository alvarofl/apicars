    package Model;
    import java.util.List;
    public class CarModel {
        private String fabricante;
            private String nome;
                private List<String> modelo;
            public  CarModel() {}
                public CarModel (String fabricante, String nome, List<String> modelo) {
                    this.fabricante = fabricante;
                    this.nome = nome;
                this.modelo = modelo;
                }
            
                    public String  getFabricante() {return fabricante;}
                            public void setFabricante(String fabricante) { this.fabricante = fabricante;}
                                public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<String> getModelo() { return modelo; }
    public void setModelo(List<String> modelo) { this.modelo = modelo; }   
        }
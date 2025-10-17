    package com.example.api.server;

    import com.example.api.model.Car;
    import com.example.api.repository.CarRepository;
        //esse google gson é uma biblioteca para converter objetos 
    import com.google.gson.Gson;
    //esse sun net serve para criar servidores http simples
        import com.sun.net.httpserver.*;
    import java.io.IOException;
    import java.io.OutputStream;
//importante para trabalhar uri stream serve para trabalhar com fluxos de dados 
import java.net.InetSocketAddress;
    //esse net Inet representa um endereço ip e uma porta
        import java.util.List;
public class HttpServerApi{
            //definindo a porta do servidor
                private static final  int PORT = 8080;
    private final CarRepository repository;
    private final Gson gson = new Gson();
        public HttpServer(CarRepository repository) {
//inicialisando o repositório 
                this.repository = repository;
        }
            public void start() throws IOException {
                HttpServer server = HttpServer.create(new InetSockeAddress(PORT), 0);
        server.createContext("/cars", this::handleCars);
        server.createContext("/car", this::handleCarByName);
        server.setExecutor(null);
        System.out.println("🚗 Servidor iniciado em http://localhost:" + PORT);
        server.start();
    }

    private void handleCars(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Método não permitido");
            return;
        }

        List<Car> cars = repository.getAll();
        sendResponse(exchange, 200, gson.toJson(cars));
    }

    private void handleCarByName(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Método não permitido");
            return;
        }

        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("name=")) {
            sendResponse(exchange, 400, "Parâmetro 'name' é obrigatório");
            return;
        }

        String name = query.split("=")[1];
        Car car = repository.getByName(name);
        if (car == null) {
            sendResponse(exchange, 404, "Carro não encontrado");
        } else {
            sendResponse(exchange, 200, gson.toJson(car));
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
                }
}   
}
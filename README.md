rm .\src\pedidos_semana1Escrita.csv,  .\src\pedidos_semana2Escrita.csv,  .\src\pedidos_semana3Escrita.csv,  .\src\pedidos_semana4Escrita.csv, .\log_canceladosSemana2,  .\log_canceladosSemana3,  .\log_canceladosSemana4, .\log_entregasSemana3,  .\log_entregasSemana4, .\log_fabricacaoSemana1, .\log_fabricacaoSemana2, .\log_fabricacaoSemana3, .\log_fabricacaoSemana4,  .\dados.dat

// P1
//├── LeitorClientes   → clientes.csv
//├── LeitorPedidos    → pedidos_semana1.csv.csv (mín. 10 pedidos) 4 chamadas em p1 assim: new LeitorPedidos(clientes, "pedidos_semana1.csv", "registros_semana1.csv").exportar();
//├── LeitorPedidos    → pedidos_semana2.csv.csv (mín. 10 pedidos)
//├── LeitorPedidos    → pedidos_semana3.csv.csv (mín. 10 pedidos)
//├── LeitorPedidos    → pedidos_semana4.csv.csv (mín. 10 pedidos)
//└── serializa tudo   → dados.dat
//
//P2
//├── desserializa dados.dat
//├── simula semana 1 → EscritorFabricacao, EscritorCancelados
//├── simula semana 2 → EscritorFabricacao, EscritorCancelados, EscritorEntregas
//├── simula semana 3 → idem
//├── simula semana 4 → idem
//└── EscritorLog     → ao longo de tudo

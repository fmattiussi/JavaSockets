# JavaSockets
JavaSockets è un programma CLI che permette la connessione a un host e la comunicazione tramite dei stream socket. 

All'interno del programma è integrato un piccolo sistema di "rubrica" in cui è possibile salvare i parametri di connessione degli host a cui ci si collega più spesso.

Questo programma è un progetto scolastico e il suo sviluppo non è attivo.

## Installazione

Nella repository sono presenti i file di progetto di Eclipse nella versione 2023-03 (4.27.0). Il progetto è stato compilato utilizzando un runtime environment JavaSE 17.

Una versione JAR è disponibile nella sezione delle releases.

## Utilizzo client-side

Per questa versione del programma, lo scambio di informazioni avviene su una connessione, come implementazione del protocollo TCP. 

È inteso che da anteporsi a questi comandi va posto il percorso in cui si trova l'eseguibile del programma client.

### Connessione a un host generico

Per host generico si intende un host che non è stato precedentemente salvato, quindi che richiede di specificare i relativi parametri di connessione

	connect new indirzzo_host porta_host

### Salvataggio di un nuovo host nell'addressbook

Per il salvataggio di un nuovo host sono richiesti almeno questi parametri. Nell'addressbook l'host avrà come ID il nome specificato nel parametro nome_host, che si raccomanda di impostare in modo che sia diverso dall'indirizzo effettivo dell'host.

	addressbook add nome_host indirizzo_host porta_host

Nel caso che l'host preveda un sistema di autenticazione username/password, questi parametri si potranno specificare nel comando

    addressbook add nome_host indirizzo_host porta_host username_host password_host
	
### Connessione a un host salvato

	connect to nome_host
	
### Visualizzazione degli host salvati

Oltre a elencare gli host salvati, questo comando specificherà se per l'host salvato è previsto un metodo di autenticazione. Per privacy viene specificato tramite un flag, senza mostrare le credenziali salvate.

	addressbook list

Che fornirà un output simile a questo

    Host salvati:

	    * [laboratorio_e011] [no auth] {address: 10.3.0.50, port: 8546}

	    * [github_server] [auth] {address: github.com, port: 2964}
	
### Eliminazione di un host salvato

Non è previsto un metodo di modifica diretta di un host salvato, la soluzione è l'eliminazione e il conseguenza risalvataggio dell'host (come mostrato nel secondo punto).

	addressbook delete nome_host


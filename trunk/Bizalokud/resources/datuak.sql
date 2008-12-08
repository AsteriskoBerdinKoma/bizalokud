-- BizAloKud aplikaziorako adibide-datuak
--
-- Host: localhost    Database: bizalokud
-- ------------------------------------------------------
-- Datu-base eskemaren data: 	2008-12-05-00:30

USE `bizalokud`;

--
-- 'gunea' taulako datuak
--
INSERT INTO `gunea` SET alta=1, toki_kop=40, izena='Unibertsitateak', helb='Paseo de Manuel Lardizábal, 1 20018', ip='127.0.0.1', lat=43.307274, lon=-2.0115459;
INSERT INTO `gunea` SET alta=1, toki_kop=20, izena='Benta-berri', helb='Plaza de José María Sert, 20018', ip='192.160.1.97', lat=43.3106089, lon=-2.0065429;
INSERT INTO `gunea` SET alta=1, toki_kop=15, izena='Hondarreta', helb='Paseo de Eduardo Chillida, 20008', ip='192.160.1.98', lat=43.3188779, lon=-2.0050853;
INSERT INTO `gunea` SET alta=1, toki_kop=25, izena='La Perla', helb='Paseo de Miraconcha, 1 20007', ip='192.160.1.99', lat=43.3152454, lon=-1.9879533;
INSERT INTO `gunea` SET alta=1, toki_kop=30, izena='Boulevard', helb='Calle de Hernani, 1 20003', ip='192.160.1.100', lat=43.3214625, lon=-1.9849477;
INSERT INTO `gunea` SET alta=1, toki_kop=20, izena='Anoeta', helb='Paseo de Anoeta, 1 20010', ip='192.160.1.101', lat=43.3020628, lon=-1.9755453;
INSERT INTO `gunea` SET alta=1, toki_kop=15, izena='Kursaal', helb='Calle de Usandizaga, 1 20002', ip='192.160.1.102', lat=43.3231369, lon=-1.9787117;
INSERT INTO `gunea` SET alta=1, toki_kop=15, izena='Sagues', helb='Paseo de José Miguel Barandiarán, 10 20013', ip='192.160.1.103', lat=43.3270433, lon=-1.9711805;
INSERT INTO `gunea` SET alta=1, toki_kop=15, izena='Garbera', helb='Travesía de Garbera, 20017', ip='192.160.1.104', lat=43.3098332, lon=-1.9467818;
INSERT INTO `gunea` SET alta=1, toki_kop=15, izena='Intxaurrondo', helb='Paseo de Galicia, 20015', ip='192.160.1.105', lat=43.3121455, lon=-1.9547526;
INSERT INTO `gunea` SET alta=1, toki_kop=15, izena='Altza', helb='Paseo de Larratxo, 20017', ip='192.160.1.106', lat=43.3157652, lon=-1.9372317;
INSERT INTO `gunea` SET alta=1, toki_kop=10, izena='Easo', helb='Calle de la Autonomía, 4 20006', ip='192.160.1.107', lat=43.3135866, lon=-1.9823996;
INSERT INTO `gunea` SET alta=1, toki_kop=10, izena='Añorga', helb='Av de Añorga, 8 20018', ip='192.160.1.108', lat=43.291948, lon=-1.995245;
INSERT INTO `gunea` SET alta=1, toki_kop=10, izena='Martutene', helb='Paseo de Martutene, 1 20014', ip='192.160.1.109', lat=43.3020901, lon=-1.9574149;
INSERT INTO `gunea` SET alta=1, toki_kop=10, izena='Loiola', helb='Camino de Kristobaldegi, 20014', ip='192.160.1.110', lat=43.3056027, lon=-1.9619864;
INSERT INTO `gunea` SET alta=1, toki_kop=15, izena='Igara', helb='Calle de Portuetxe, 20018', ip='192.160.1.111', lat=43.3013323, lon=-2.0154761;
INSERT INTO `gunea` SET alta=1, toki_kop=10, izena='Errotaburu', helb='Paseo de Orixe, 20018', ip='192.160.1.112', lat=43.300579, lon=-2.0050767;
INSERT INTO `gunea` SET alta=1, toki_kop=5, izena='Igeldo', helb='Paseo de Igeldo, 20008', ip='192.160.1.113', lat=43.3182783, lon=-2.0120229;
INSERT INTO `gunea` SET alta=1, toki_kop=10, izena='Aiete', helb='Calle de Borroto, 20009', ip='192.160.1.114', lat=43.3023687, lon=-1.9887946;

--
-- 'erabiltzailea' taulako datuak
--
INSERT INTO erabiltzailea SET nan='00000000A',pasahitza=sha1('admin'),izena='Admin',abizenak='',alta=1,mota='admin',eposta='admin@bizalokud.net',telefonoa='943012343';
INSERT INTO erabiltzailea SET nan='34794120L',pasahitza=sha1('erab'),izena='Antton',abizenak='Abando Gurutzeta',alta=1,mota='erab',eposta='antton@hotmail.com',telefonoa='456897856';
INSERT INTO erabiltzailea SET nan='10200056Q',pasahitza=sha1('erab'),izena='Gerbasio',abizenak='Madariaga García',alta=1,mota='erab',eposta='gerbasio@yahoo.es',telefonoa='156845687';
INSERT INTO erabiltzailea SET nan='33534809G',pasahitza=sha1('erab'),izena='Mikel',abizenak='Pagadizabal Kishcner',alta=1,mota='erab',eposta='mikel23@gmail.com',telefonoa='684459121';
INSERT INTO erabiltzailea SET nan='73079339J',pasahitza=sha1('erab'),izena='Jon',abizenak='Jakaxuri Zubiri',alta=1,mota='erab',eposta='jon_jaka@mail.com',telefonoa='675458915';
INSERT INTO erabiltzailea SET nan='18422984F',pasahitza=sha1('erab'),izena='Txomin',abizenak='Ximeno Gutierrez',alta=1,mota='erab',eposta='tx_x@aol.com',telefonoa='698352688';
INSERT INTO erabiltzailea SET nan='00823572B',pasahitza=sha1('erab'),izena='Juan Kruz',abizenak='Zabalegi Lizarazu',alta=1,mota='erab',eposta='jkz@ehu.es',telefonoa='654102589';
INSERT INTO erabiltzailea SET nan='48476223L',pasahitza=sha1('erab'),izena='Ander',abizenak='Deba Etxeberria',alta=1,mota='erab',eposta='andertxo@hotmail.com',telefonoa='630214888';
INSERT INTO erabiltzailea SET nan='09808637B',pasahitza=sha1('erab'),izena='Jokin',abizenak='Harizmendi Agirre',alta=1,mota='erab',eposta='jokin1@berria.com',telefonoa='699100125';
INSERT INTO erabiltzailea SET nan='34772079N',pasahitza=sha1('erab'),izena='Ibon',abizenak='Nagiola Erregerena',alta=1,mota='erab',eposta='ibon_gab@euskadi.net',telefonoa='678541023';
INSERT INTO erabiltzailea SET nan='09760589X',pasahitza=sha1('erab'),izena='Miriam',abizenak='Gabilondo Padilla',alta=1,mota='erab',eposta='miri_mx@mixmail.net',telefonoa='689125730';
INSERT INTO erabiltzailea SET nan='78894822S',pasahitza=sha1('erab'),izena='Andrea',abizenak='Kamino Grande',alta=1,mota='erab',eposta='andreita@euskalnet.net',telefonoa='914568475';
INSERT INTO erabiltzailea SET nan='10202681L',pasahitza=sha1('erab'),izena='Leire',abizenak='Rekarte López',alta=1,mota='erab',eposta='leirek@hotmail.com',telefonoa='942567128';
INSERT INTO erabiltzailea SET nan='09726776F',pasahitza=sha1('erab'),izena='Maite',abizenak='Sagardoi Smith',alta=1,mota='erab',eposta='maite_123@gmail.com',telefonoa='943598201';
INSERT INTO erabiltzailea SET nan='03104202F',pasahitza=sha1('erab'),izena='Oihane',abizenak='Adoriaga Sultán',alta=1,mota='erab',eposta='oihane_ad@mailinator.com',telefonoa='943569102';
INSERT INTO erabiltzailea SET nan='71440751Z',pasahitza=sha1('erab'),izena='Nerea',abizenak='Paskual Rodríguez',alta=1,mota='erab',eposta='nere_paskual@hotmail.com',telefonoa='945687136';
INSERT INTO erabiltzailea SET nan='09768316D',pasahitza=sha1('erab'),izena='Maitane',abizenak='Peñagarikano Bustos',alta=1,mota='erab',eposta='maitanep001@ehu.es',telefonoa='948200315';
INSERT INTO erabiltzailea SET nan='33525035M',pasahitza=sha1('erab'),izena='Aitziber',abizenak='Tellaetxe Unamuno',alta=1,mota='erab',eposta='aitziber_tu@gmail.com',telefonoa='948198634';
INSERT INTO erabiltzailea SET nan='48395800M',pasahitza=sha1('erab'),izena='Lorea',abizenak='Ubegi Abadiño',alta=1,mota='erab',eposta='lorea@yahoo.es',telefonoa='685149835';
INSERT INTO erabiltzailea SET nan='33457055J',pasahitza=sha1('erab'),izena='Nora',abizenak='Foronda Otaegi',alta=1,mota='erab',eposta='nora2@correo.es',telefonoa='658100790';
INSERT INTO erabiltzailea SET nan='34248879Q',pasahitza=sha1('erab'),izena='Eider',abizenak='Egaña Salvador',alta=1,mota='erab',eposta='egana_e@yahoo.es',telefonoa='612358974';
INSERT INTO erabiltzailea SET nan='71552044X',pasahitza=sha1('erab'),izena='Estitxu',abizenak='Egibar Guevara',alta=1,mota='erab',eposta='esti@iespana.es',telefonoa='601458630';
INSERT INTO erabiltzailea SET nan='05421792W',pasahitza=sha1('erab'),izena='Joxe',abizenak='Aldasoro Mundariz',alta=1,mota='erab',eposta='alda_joxe@mail.com',telefonoa='658127548';
INSERT INTO erabiltzailea SET nan='72882671H',pasahitza=sha1('erab'),izena='Gorka',abizenak='Urmeneta Acedo',alta=1,mota='erab',eposta='urmenetag@ehu.es',telefonoa='958361820';
INSERT INTO erabiltzailea SET nan='28536297J',pasahitza=sha1('erab'),izena='Ioritz',abizenak='Sagastizabal Barbeito',alta=1,mota='erab',eposta='sagastioritz@herald.us',telefonoa='699502358';
INSERT INTO erabiltzailea SET nan='12400029Q',pasahitza=sha1('erab'),izena='Gotzon',abizenak='Uranga Barón',alta=1,mota='erab',eposta='gotzon_autentico@hotmail.com',telefonoa='632102587';
INSERT INTO erabiltzailea SET nan='27484749W',pasahitza=sha1('erab'),izena='Alberto',abizenak='Saez Cabezón',alta=1,mota='erab',eposta='alberrito@hotmail.com',telefonoa='987952018';
INSERT INTO erabiltzailea SET nan='48415062Q',pasahitza=sha1('erab'),izena='Pedro',abizenak='Garcia Casillas',alta=1,mota='erab',eposta='pedrogarci@hotmail.com',telefonoa='963258417';
INSERT INTO erabiltzailea SET nan='20032793T',pasahitza=sha1('erab'),izena='Koldo',abizenak='Simon',alta=1,mota='erab',eposta='koldo_s@gmail.com',telefonoa='955651520';
INSERT INTO erabiltzailea SET nan='44914623Q',pasahitza=sha1('erab'),izena='Txema',abizenak='Ortiz',alta=1,mota='erab',eposta='txem_or@gmail.es',telefonoa='688784102';
INSERT INTO erabiltzailea SET nan='71507006Y',pasahitza=sha1('erab'),izena='Aintzane',abizenak='Calvo Oñarbi',alta=1,mota='erab',eposta='calvo_aintzane@yahoo.es',telefonoa='600352594';
INSERT INTO erabiltzailea SET nan='09204851K',pasahitza=sha1('erab'),izena='Janire',abizenak='Sotelo Urdanpileta',alta=1,mota='erab',eposta='sotelo_janire@correo.es',telefonoa='698456810';
INSERT INTO erabiltzailea SET nan='16805606N',pasahitza=sha1('erab'),izena='Enara',abizenak='Gil Espinoso',alta=1,mota='erab',eposta='gil_enara@herria.org',telefonoa='697196833';
INSERT INTO erabiltzailea SET nan='48497167B',pasahitza=sha1('erab'),izena='Lidia',abizenak='Azkue Cortés',alta=1,mota='erab',eposta='lidia_c@gigamail.es',telefonoa='622103388';
INSERT INTO erabiltzailea SET nan='48481568Y',pasahitza=sha1('erab'),izena='Elena',abizenak='Txintxurreta Fresnedo',alta=1,mota='erab',eposta='elenatxin@mailinator.com',telefonoa='633585598';
INSERT INTO erabiltzailea SET nan='23021155H',pasahitza=sha1('erab'),izena='Agurtzane',abizenak='González Gabiña',alta=1,mota='erab',eposta='agur_gab@gmail.com',telefonoa='666489888';
INSERT INTO erabiltzailea SET nan='11774391R',pasahitza=sha1('erab'),izena='Joseba',abizenak='Pérez Goya',alta=1,mota='erab',eposta='josebap@ehu.es',telefonoa='615248655';
INSERT INTO erabiltzailea SET nan='31687116Q',pasahitza=sha1('erab'),izena='Patxi',abizenak='Alkorta Arregi',alta=1,mota='erab',eposta='palkorta001@ehu.es',telefonoa='625489911';
INSERT INTO erabiltzailea SET nan='13724732B',pasahitza=sha1('erab'),izena='Xabier',abizenak='Telletxea Berrio',alta=1,mota='erab',eposta='telle_xabi@yahoo.es',telefonoa='658471002';
INSERT INTO erabiltzailea SET nan='24309084Q',pasahitza=sha1('erab'),izena='Hodei',abizenak='Arruabarrena Hernandez',alta=1,mota='erab',eposta='vector_arrow@hotmail.com',telefonoa='943158674';
INSERT INTO erabiltzailea SET nan='16802039X',pasahitza=sha1('erab'),izena='Aingeru',abizenak='Perona Iza',alta=1,mota='erab',eposta='savior_me@hotmail.com',telefonoa='943685741';
INSERT INTO erabiltzailea SET nan='16798052W',pasahitza=sha1('erab'),izena='Angela',abizenak='Menendez Messina',alta=1,mota='erab',eposta='boring_thing@hitza.org',telefonoa='968352514';

--
-- 'bizikleta' taulako datuak
--

INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=1;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Txuria', fk_uneko_gune_id=null, fk_jatorri_gune_id=1;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=1;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=1;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Urdina', fk_uneko_gune_id=null, fk_jatorri_gune_id=1;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Urdina', fk_uneko_gune_id=null, fk_jatorri_gune_id=1;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Urdina', fk_uneko_gune_id=null, fk_jatorri_gune_id=2;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Urdina', fk_uneko_gune_id=null, fk_jatorri_gune_id=2;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='matxuratuta', modeloa='Orbea CTX200', kolorea='Berdea', fk_uneko_gune_id=3, fk_jatorri_gune_id=2;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=2;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=1, fk_jatorri_gune_id=2;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Berdea', fk_uneko_gune_id=null, fk_jatorri_gune_id=3;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='konpontzen', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=2, fk_jatorri_gune_id=3;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=3;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='matxuratuta', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=3, fk_jatorri_gune_id=3;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Berdea', fk_uneko_gune_id=null, fk_jatorri_gune_id=3;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Txuria', fk_uneko_gune_id=null, fk_jatorri_gune_id=3;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=4, fk_jatorri_gune_id=4;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Berdea', fk_uneko_gune_id=null, fk_jatorri_gune_id=4;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=5, fk_jatorri_gune_id=4;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=6, fk_jatorri_gune_id=4;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='galduta', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=null, fk_jatorri_gune_id=4;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=4;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=4;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=7, fk_jatorri_gune_id=5;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=7, fk_jatorri_gune_id=5;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=1, fk_jatorri_gune_id=5;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=1, fk_jatorri_gune_id=5;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Txuria', fk_uneko_gune_id=null, fk_jatorri_gune_id=5;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='galduta', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=5;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Txuria', fk_uneko_gune_id=null, fk_jatorri_gune_id=5;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=4, fk_jatorri_gune_id=5;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=5, fk_jatorri_gune_id=6;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=6, fk_jatorri_gune_id=6;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=6, fk_jatorri_gune_id=6;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=1, fk_jatorri_gune_id=6;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=1, fk_jatorri_gune_id=6;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=1, fk_jatorri_gune_id=6;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='konpontzen', modeloa='Orbea CTX200', kolorea='Txuria', fk_uneko_gune_id=1, fk_jatorri_gune_id=7;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=1, fk_jatorri_gune_id=7;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=1, fk_jatorri_gune_id=7;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=7;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=7;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=null, fk_jatorri_gune_id=8;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='galduta', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=8;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=null, fk_jatorri_gune_id=9;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='matxuratuta', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=3, fk_jatorri_gune_id=9;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Txuria', fk_uneko_gune_id=3, fk_jatorri_gune_id=9;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=8, fk_jatorri_gune_id=10;
INSERT INTO `bizikleta`SET alokatuta=0, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Txuria', fk_uneko_gune_id=5, fk_jatorri_gune_id=10;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=10;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=10;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='galduta', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=null, fk_jatorri_gune_id=6;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=6;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=null, fk_jatorri_gune_id=6;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=6;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Gorria', fk_uneko_gune_id=null, fk_jatorri_gune_id=6;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Txuria', fk_uneko_gune_id=null, fk_jatorri_gune_id=7;
INSERT INTO `bizikleta`SET alokatuta=1, alta=1, egoera='ondo', modeloa='Orbea CTX200', kolorea='Beltza', fk_uneko_gune_id=null, fk_jatorri_gune_id=7;
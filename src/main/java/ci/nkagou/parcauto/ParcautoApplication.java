package ci.nkagou.parcauto;

import ci.nkagou.parcauto.dtos.vehicule.VehiculeDtoOut;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.Couleur;
import ci.nkagou.parcauto.enums.Genre;
import ci.nkagou.parcauto.enums.Statut;
import ci.nkagou.parcauto.enums.StatutChauffeur;
import ci.nkagou.parcauto.repositories.*;
import ci.nkagou.parcauto.services.*;
import ci.nkagou.parcauto.utils.EncrytedPasswordUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.util.HtmlUtils;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ParcautoApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(ParcautoApplication.class, args);

		DmdService dmdService = ctx.getBean(DmdService.class);
		DirectionService directionService = ctx.getBean(DirectionService.class);
        EmployeDmdRepository employeDmdRepository = ctx.getBean(EmployeDmdRepository.class);
		AttributionRepository attributionRepository = ctx.getBean(AttributionRepository.class);
		MarqueService marqueService = ctx.getBean (MarqueService.class);
        TypevehiculeService typevehiculeService = ctx.getBean(TypevehiculeService.class);
        SiteService siteService = ctx.getBean(SiteService.class);


		Marque marque8 = new Marque();
		marque8.setName("Bugatti");

        //marqueService.create(marque8);

		Typevehicule typevehicule3 = new Typevehicule();
		typevehicule3.setLibelle("Sport");

		//typevehiculeService.create(typevehicule3);


		/*Attribution attribution = new Attribution();

		attribution.setDateAttribution(LocalDate.of(2022,12, 14));*/

		//VALIDATION EMPLOYEDMD
		/*Direction direction = directionService.findById(1L);*/

		//List<EmployeDmd> dmds = dmdService.listEmployeDmdByStatutDirection(Statut.DEMANDE, direction);

        //List<EmployeDmd> dmdss = dmdService.listEmployeDmdsByStatut(Statut.VALIDATION);

        //String d = "";
		/*RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		UserRepository userRepository = ctx.getBean(UserRepository.class);

		AppRole roleuser = roleRepository.findByRoleId(3L);
		AppRole roleadmin = roleRepository.findByRoleId(1L);
		AppRole roleaccess = roleRepository.findByRoleId(2L);

		AppUser admin = userRepository.getById(1L);
		AppUser user = userRepository.getById(2L);
		AppUser access = userRepository.getById(3L);


		System.out.println("===============AFFECTATION ROLE PAR USER=======================");
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		userRoleRepository.save(new UserRole(admin,roleadmin));
		userRoleRepository.save(new UserRole(admin,roleuser));
		userRoleRepository.save(new UserRole(admin,roleaccess));
		userRoleRepository.save(new UserRole(access,roleaccess));
		userRoleRepository.save(new UserRole(user,roleuser));
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&FIN DE LA TRANSACTION&&&&&&&&&&&&&&&&&&&");*/

		/*System.out.println("===============DEBUT TRANSACTION=======================");

		EmployeService employeService = ctx.getBean(EmployeService.class);
		SiteService siteService = ctx.getBean(SiteService.class);
		DirectionService directionService = ctx.getBean(DirectionService.class);
		DmdService dmdService = ctx.getBean(DmdService.class);
		EmployeDmdService employeDmdService = ctx.getBean(EmployeDmdService.class);

		Direction directionPRODUCTION =directionService.findById(2L);
		Direction directionTECHNIQUE =directionService.findById(3L);
		Direction directionDSI =directionService.findById(1L);
		Site siteProduction = siteService.findById(2L);
		Site sitePerso = siteService.findById(1L);

		Employe employeNkagou = new Employe();
		employeNkagou.setNumMatEmpl(100L);
		employeNkagou.setNom("N'KAGOU".toUpperCase());
		employeNkagou.setPrenom("GHISLAIN".toUpperCase());
		employeNkagou.setDateEmbauche(LocalDate.of(2000,12, 12));
		employeNkagou.setDateNaissance(LocalDate.of(1980,1, 1));
		employeNkagou.setEmail("ghislain.nkagou@ics.ci");
		employeNkagou.setFonction("Informaticien");
		employeNkagou.setGenre(Genre.MASCULIN);
		employeNkagou.setEstSuperieureHierachique(false);
		employeNkagou.setEstChauffeur(false);
		employeNkagou.setTelephoneEmploye("0301010101");
		employeNkagou.setDirection(directionDSI);
		employeNkagou.setSite(siteProduction);
		Employe employeNkagou1 = employeService.create(employeNkagou);


		Employe employeBamba = new Employe();
		employeBamba.setNumMatEmpl(101L);
		employeBamba.setNom("BAMBA".toUpperCase());
		employeBamba.setPrenom("YAKIE".toUpperCase());
		employeBamba.setDateEmbauche(LocalDate.of(2007,1, 12));
		employeBamba.setDateNaissance(LocalDate.of(1987,1, 1));
		employeBamba.setEmail("yakie.bamba@ics.ci");
		employeBamba.setFonction("Informaticien");
		employeBamba.setGenre(Genre.MASCULIN);
		employeBamba.setEstSuperieureHierachique(false);
		employeBamba.setEstChauffeur(false);
		employeBamba.setTelephoneEmploye("0401010101");
		employeBamba.setDirection(directionDSI);
		employeBamba.setSite(siteProduction);
		Employe employeBamba1 = employeService.create(employeBamba);

		Employe employeLanzeni = new Employe();
		employeLanzeni.setNumMatEmpl(19L);
		employeLanzeni.setNom("OUATTARA".toUpperCase());
		employeLanzeni.setPrenom("LANZENI".toUpperCase());
		employeLanzeni.setDateEmbauche(LocalDate.of(2003,11, 18));
		employeLanzeni.setDateNaissance(LocalDate.of(1960,1, 10));
		employeLanzeni.setEmail("lanzeni.ouattara@ics.ci");
		employeLanzeni.setFonction("Directeur des Systèmes d'information");
		employeLanzeni.setGenre(Genre.MASCULIN);
		employeLanzeni.setEstSuperieureHierachique(true);
		employeLanzeni.setEstChauffeur(false);
		employeLanzeni.setTelephoneEmploye("0601010101");
		employeLanzeni.setDirection(directionDSI);
		employeLanzeni.setSite(siteProduction);
		Employe employeLanzeni1 = employeService.create(employeLanzeni);

		Employe employeGoubo = new Employe();
		employeGoubo.setNumMatEmpl(204L);
		employeGoubo.setNom("GOUBO".toUpperCase());
		employeGoubo.setPrenom("CEDRIC".toUpperCase());
		employeGoubo.setDateEmbauche(LocalDate.of(2022,11, 18));
		employeGoubo.setDateNaissance(LocalDate.of(2010,1, 10));
		employeGoubo.setEmail("cedric.goubo@ics.ci");
		employeGoubo.setFonction("Informaticien");
		employeGoubo.setGenre(Genre.MASCULIN);
		employeGoubo.setEstSuperieureHierachique(false);
		employeGoubo.setEstChauffeur(false);
		employeGoubo.setTelephoneEmploye("0791010101");
		employeGoubo.setDirection(directionDSI);
		employeGoubo.setSite(siteProduction);
		Employe employeGoubo1 = employeService.create(employeGoubo);

		Employe employeNdri = new Employe();
		employeNdri.setNumMatEmpl(20L);
		employeNdri.setNom("N'DRI".toUpperCase());
		employeNdri.setPrenom("GREGOIRE".toUpperCase());
		employeNdri.setDateEmbauche(LocalDate.of(2004,11, 23));
		employeNdri.setDateNaissance(LocalDate.of(1961,10, 17));
		employeNdri.setEmail("gregoire.ndri@ics.ci");
		employeNdri.setFonction("Responsable Parc Auto");
		employeNdri.setGenre(Genre.MASCULIN);
		employeNdri.setEstSuperieureHierachique(true);
		employeNdri.setEstChauffeur(false);
		employeNdri.setTelephoneEmploye("0601010101");
		employeNdri.setDirection(directionTECHNIQUE);
		employeNdri.setSite(siteProduction);
		Employe employeNdri1 = employeService.create(employeNdri);

		Employe employeToure = new Employe();
		employeToure.setNumMatEmpl(26L);
		employeToure.setNom("TOURE".toUpperCase());
		employeToure.setPrenom("MAMADOU".toUpperCase());
		employeToure.setDateEmbauche(LocalDate.of(2005,11, 27));
		employeToure.setDateNaissance(LocalDate.of(1962,11, 19));
		employeToure.setEmail("mamadou.toure@ics.ci");
		employeToure.setFonction("Chauffeur");
		employeToure.setGenre(Genre.MASCULIN);
		employeToure.setEstSuperieureHierachique(false);
		employeToure.setEstChauffeur(true);
		employeToure.setTelephoneEmploye("0999010101");
		employeToure.setDirection(directionTECHNIQUE);
		employeToure.setSite(siteProduction);
		Employe employeToure1 = employeService.create(employeToure);

		Employe employeUser = new Employe();
		employeUser.setNumMatEmpl(31L);
		employeUser.setNom("user".toUpperCase());
		employeUser.setPrenom("user".toUpperCase());
		employeUser.setDateEmbauche(LocalDate.of(2015,11, 27));
		employeUser.setDateNaissance(LocalDate.of(1969,11, 19));
		employeUser.setEmail("user@ics.ci");
		employeUser.setFonction("User");
		employeUser.setGenre(Genre.MASCULIN);
		employeUser.setEstSuperieureHierachique(false);
		employeUser.setEstChauffeur(false);
		employeUser.setTelephoneEmploye("09990101010");
		employeUser.setDirection(directionTECHNIQUE);
		employeUser.setSite(siteProduction);
		Employe employeUser1 = employeService.create(employeUser);

		Employe employeAdmin = new Employe();
		employeAdmin.setNumMatEmpl(299L);
		employeAdmin.setNom("admin".toUpperCase());
		employeAdmin.setPrenom("admin".toUpperCase());
		employeAdmin.setDateEmbauche(LocalDate.of(2005,11, 27));
		employeAdmin.setDateNaissance(LocalDate.of(1990,11, 19));
		employeAdmin.setEmail("admin@ics.ci");
		employeAdmin.setFonction("Admin");
		employeAdmin.setGenre(Genre.MASCULIN);
		employeAdmin.setEstSuperieureHierachique(false);
		employeAdmin.setEstChauffeur(false);
		employeAdmin.setTelephoneEmploye("09990101011");
		employeAdmin.setDirection(directionTECHNIQUE);
		employeAdmin.setSite(sitePerso);
		Employe employeAdmin1 = employeService.create(employeAdmin);

		Employe employeAccess = new Employe();
		employeAccess.setNumMatEmpl(223L);
		employeAccess.setNom("access".toUpperCase());
		employeAccess.setPrenom("access".toUpperCase());
		employeAccess.setDateEmbauche(LocalDate.of(2019,11, 29));
		employeAccess.setDateNaissance(LocalDate.of(1968,11, 19));
		employeAccess.setEmail("access@ics.ci");
		employeAccess.setFonction("Access");
		employeAccess.setGenre(Genre.MASCULIN);
		employeAccess.setEstSuperieureHierachique(false);
		employeAccess.setEstChauffeur(false);
		employeAccess.setTelephoneEmploye("09990101015");
		employeAccess.setDirection(directionTECHNIQUE);
		employeAccess.setSite(sitePerso);
		Employe employeAccess1 = employeService.create(employeAccess);


		String p = "123";
		String password = EncrytedPasswordUtils.encrytePassword(p);

		System.out.println("===============DEBUT TRANSACTION=======================");
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser admin = userRepository.save(new AppUser(1L,"admin",password,true, employeAdmin1 ));
		AppUser user = userRepository.save(new AppUser(2L,"user",password,true, employeUser1));
		AppUser access = userRepository.save(new AppUser(3L,"access",password,true, employeAccess1));
		AppUser ghislain = userRepository.save(new AppUser("ghislain", password, true, employeNkagou1));
		AppUser yakie = userRepository.save(new AppUser("yakie", password, true, employeBamba1));
		AppUser lanzeni = userRepository.save(new AppUser("lanzeni", password, true, employeLanzeni1));
		AppUser cedric = userRepository.save(new AppUser("cedric", password, true, employeGoubo1));
		AppUser toure = userRepository.save(new AppUser("toure", password, true, employeToure1));
		AppUser ndri = userRepository.save(new AppUser("ndri", password, true, employeNdri1));
		//AppUser cedric = userRepository.save(new AppUser("cedric", password, true, employeGoubo1));
		System.out.println("===============AJOUT CLIENT=======================");
		userRepository.findAll().forEach(u->System.out.println(u.getUserName()));
		System.out.println("Utilisateurs ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(
				"===============AJOUT ROLE=======================");
		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);

		AppRole roleresponsable = roleRepository.save(new AppRole("ROLE_RESPONSABLE"));
		AppRole roleparcauto = roleRepository.save(new AppRole("ROLE_PARCAUTO"));
		AppRole roleuser = roleRepository.findByRoleId(3L);
		roleRepository.findAll().forEach(u->System.out.println(u.getRoleName()));
		System.out.println("Roles ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AFFECTATION ROLE PAR USER=======================");
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		userRoleRepository.save(new UserRole(ndri,roleparcauto));
		userRoleRepository.save(new UserRole(ghislain,roleuser));
		userRoleRepository.save(new UserRole(cedric,roleuser));
		userRoleRepository.save(new UserRole(yakie,roleuser));
		userRoleRepository.save(new UserRole(toure,roleuser));
		userRoleRepository.save(new UserRole(lanzeni,roleresponsable));

		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&FIN DE LA TRANSACTION&&&&&&&&&&&&&&&&&&&");*/


	/*	System.out.println("List des employés");
		List<Site> sites = siteService.all();
		Optional<Site> site = siteService.findById(1L);*/

		//Dmd mydmd = dmdService.getById(2L);
		//Optional<Dmd> dmd = dmdService.findById(2L);
		/*Dmd dmd = dmdService.findbyIDS(2L);
		List<Employe> employes1 = dmd.getEmployes();

		employes1.forEach(u -> System.out.println( u.getNom()));*/

		//EmployeDmd employeDmd = new EmployeDmd();


		//System.out.println("Dmd ajoutées avec succes");


		/*Site sitePerso = new Site("CENTRE DE PERSO");
		Site siteProduction = new Site("PRODUCTION");
		Site siteSiege = new Site("SIEGE");
		siteService.create(sitePerso);
		siteService.create(siteProduction);
		siteService.create(siteSiege);
		System.out.println("Sites ajoutés avec succes");
		siteService.all().forEach(u -> System.out.println(u.getLibelle()));
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("===============AJOUT DIRECTION=======================");

		Direction directionDSI = new Direction("DSI");
		Direction directionPRODUCTION = new Direction("DIRECTION PRODUCTION");
		Direction directionTECNHIQUE = new Direction("DIRECTION TECNHIQUE");
		Direction directionDAF = new Direction("DAF");
		directionService.create(directionDSI);
		directionService.create(directionPRODUCTION);
		directionService.create(directionTECNHIQUE);
		directionService.create(directionDAF);
		System.out.println("Directions ajoutées avec succes");
		directionService.all().forEach(u->System.out.println(u.getLibelle()));
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");


		Employe employeNkagou = new Employe();
		employeNkagou.setNumMatEmpl(12L);
		employeNkagou.setNom("YAO");
		employeNkagou.setPrenom("FRANCK");
		employeNkagou.setDateEmbauche(LocalDate.of(2000,12, 12));
		employeNkagou.setDateNaissance(LocalDate.of(1980,1, 1));
		employeNkagou.setEmail("franck.yao@aaa.ci");
		employeNkagou.setFonction("Agent de maitrise");
		employeNkagou.setGenre(Genre.MASCULIN);
		employeNkagou.setEstSuperieureHierachique(false);
		employeNkagou.setEstChauffeur(false);
		employeNkagou.setTelephoneEmploye("0101010101");
		employeNkagou.setDirection(directionPRODUCTION);
		employeNkagou.setSite(siteProduction);
		employeService.create(employeNkagou);

		Employe employeKone = new Employe();
		employeKone.setNumMatEmpl(13L);
		employeKone.setNom("VAKABA");
		employeKone.setPrenom("TRO");
		employeKone.setDateEmbauche(LocalDate.of(2001,11, 11));
		employeKone.setDateNaissance(LocalDate.of(1983,2, 3));
		employeKone.setEmail("tro.vakaba@aaa.ci");
		employeKone.setFonction("Agent de maitrise");
		employeKone.setGenre(Genre.FEMININ);
		employeKone.setEstSuperieureHierachique(false);
		employeKone.setEstChauffeur(false);
		employeKone.setTelephoneEmploye("0101010102");
		employeKone.setDirection(directionPRODUCTION);
		employeKone.setSite(siteProduction);
		employeService.create(employeKone);
		System.out.println("Employé ajoutés avec succes");
		employeService.all().forEach(u->System.out.println(u.getNom()));
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&FIN DE LA TRANSACTION&&&&&&&&&&&&&&&&&&&");


		String p = "123";
		String password = EncrytedPasswordUtils.encrytePassword(p);

		System.out.println("===============DEBUT TRANSACTION=======================");
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser admin = userRepository.save(new AppUser("admin",password,true));
		AppUser user = userRepository.save(new AppUser("user",password,true));
		AppUser access = userRepository.save(new AppUser("access",password,true));
		System.out.println("===============AJOUT CLIENT=======================");
		userRepository.findAll().forEach(u->System.out.println(u.getUserName()));
		System.out.println("Utilisateurs ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(
				"===============AJOUT ROLE=======================");
		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		AppRole roleadmin = roleRepository.save(new AppRole("ROLE_ADMIN"));
		AppRole roleaccess = roleRepository.save(new AppRole("ROLE_ACCES"));
		AppRole roleuser = roleRepository.save(new AppRole("ROLE_USER"));
		roleRepository.findAll().forEach(u->System.out.println(u.getRoleName()));
		System.out.println("Roles ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AFFECTATION ROLE PAR USER=======================");
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		userRoleRepository.save(new UserRole(admin,roleadmin));
		userRoleRepository.save(new UserRole(admin,roleuser));
		userRoleRepository.save(new UserRole(admin,roleaccess));
		userRoleRepository.save(new UserRole(access,roleaccess));
		userRoleRepository.save(new UserRole(user,roleuser));
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&FIN DE LA TRANSACTION&&&&&&&&&&&&&&&&&&&");*/

		/*UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser admin = userRepository.findByUserName("admin");
		List<AppRole> roles = admin.getRoles();
		roles.forEach(u -> System.out.println(u.getRoleName()));
		List<UserRole> userRoles = admin.getUserRoles();*/

		/*Employe employeNkagou = employeService.findById(1L);
		Employe employeTro = employeService.findById(2L);

		List<Employe> employes = new ArrayList<>();
		employes.add(employeTro);
		employes.add(employeNkagou);

		Dmd dmd = new Dmd();
		dmd.setMotif("Enlevement centre de perso");
		dmd.setKmDepart(10);
		dmd.setKmRetour(30);
		dmd.setHeureDepart(LocalTime.now());
		dmd.setHeureRetour(LocalTime.now());
		dmd.setDateRemplissageDmd(LocalDate.now());
		//dmd.setEmployes(employes);
		dmdService.create(dmd);

		employeDmdService.create(new EmployeDmd(employeTro,dmd, LocalDateTime.now()));
		employeDmdService.create(new EmployeDmd(employeNkagou,dmd, LocalDateTime.now()));

		Dmd dmd1 = dmdService.findById(3L);
		dmd1.getEmployes().forEach(u -> System.out.println(u.getNom()));*/

/*

		VehiculeService vehiculeService = ctx.getBean(VehiculeService.class);
		TypevehiculeService typevehiculeService = ctx.getBean(TypevehiculeService.class);
		MarqueService marqueService = ctx.getBean(MarqueService.class);

		System.out.println("===============DEBUT TRANSACTION=======================");

		typevehiculeService.create(new Typevehicule("BERLINE"));
		typevehiculeService.create(new Typevehicule("CAMION"));
		typevehiculeService.create(new Typevehicule("FOURGON"));
		typevehiculeService.create(new Typevehicule("FOURGONNETTE"));
		typevehiculeService.create(new Typevehicule("PICKUP 4x4"));
		System.out.println("===============AJOUT TYPE VEHICULE=======================");
		typevehiculeService.all().forEach(u-> System.out.println(u.getLibelle()));
		System.out.println("Type vehicule ajoutés avec succès");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");




		marqueService.create(new Marque("BMW"));
		marqueService.create(new Marque("FIAT"));
		marqueService.create(new Marque("FORD"));
		marqueService.create(new Marque("GREATWALL"));
		marqueService.create(new Marque("HYUNDAI"));
		marqueService.create(new Marque("TOYOTA"));
		System.out.println("===============AJOUT MARQUE=======================");
		marqueService.all().forEach(u -> System.out.println(u.getName()));
		System.out.println("Marques ajoutées avec succès");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AJOUT VEHICULE=======================");

		Marque marqueBmw = marqueService.findByName("BMW");
		Marque marqueHyundai = marqueService.findByName("HYUNDAI");
		Marque marqueFiat = marqueService.findByName("FIAT");
		Marque marqueGreatwall = marqueService.findByName("GREATWALL");

		Typevehicule typevehiculeBerline = typevehiculeService.findByLibelle("BERLINE");
		Typevehicule typevehiculeFourgon = typevehiculeService.findByLibelle("FOURGON");
		Typevehicule typevehiculeFourgonnette = typevehiculeService.findByLibelle("FOURGONNETTE");
		Typevehicule typevehiculePickup = typevehiculeService.findByLibelle("PICKUP 4x4");

		vehiculeService.create(new Vehicule("1234GT01", Couleur.BEIGE,LocalDate.now(), "123FRSQA", marqueBmw, typevehiculeBerline ));
		vehiculeService.create(new Vehicule("1234GT02", Couleur.NOIR,LocalDate.now(), "123FRSQB", marqueHyundai, typevehiculeFourgon ));
		vehiculeService.create(new Vehicule("1234GT03", Couleur.BLEU,LocalDate.now(), "123FRSQC", marqueFiat, typevehiculeFourgonnette ));
		vehiculeService.create(new Vehicule("1234GT04", Couleur.JAUNE,LocalDate.now(), "123FRSQC", marqueGreatwall, typevehiculePickup ));
		vehiculeService.all().forEach(u -> System.out.println(u.getImmatriculation()));
		System.out.println("Vehicules ajoutés avec succès");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============FIN TRANSACTION=======================");*/


		/*List<Vehicule> vehicules = vehiculeService.all();
		List<VehiculeDtoOut> dtos = vehiculeService.listVehiculesToDto(vehicules);
		String c = "test";*/
		EmployeService employeService = ctx.getBean(EmployeService.class);
		EmployeRepository employeRepository = ctx.getBean(EmployeRepository.class);
		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		//UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);


		Direction directionDSI = directionService.findById(2L);
		Site siteProduction = siteService.findById(2L);

		/*Employe employeAjoumani = new Employe();
		//employeKoudioRosine.setNumMatEmpl(19L);
		employeAjoumani.setNom("ADJOUMANI".toUpperCase());
		employeAjoumani.setPrenom("AIME".toUpperCase());
		employeAjoumani.setDateEmbauche(LocalDate.of(2001,04, 15));
		employeAjoumani.setDateNaissance(LocalDate.of(1960,2, 10));
		employeAjoumani.setEmail("djoum.aime@aaa.ci");
		employeAjoumani.setFonction("Directeur Production");
		employeAjoumani.setGenre(Genre.MASCULIN);
		employeAjoumani.setEstSuperieureHierachique(false);
		employeAjoumani.setEstChauffeur(false);
		employeAjoumani.setTelephoneEmploye("0601010202");
		employeAjoumani.setDirection(directionDSI);
		employeAjoumani.setSite(siteProduction);
		employeRepository.save(employeAjoumani);*/

		/*Employe employeBoa = new Employe();
		//employeKoudioRosine.setNumMatEmpl(19L);
		employeBoa.setNom("BOA".toUpperCase());
		employeBoa.setPrenom("BERANGER".toUpperCase());
		employeBoa.setDateEmbauche(LocalDate.of(2000,04, 12));
		employeBoa.setDateNaissance(LocalDate.of(1988,3, 11));
		employeBoa.setEmail("boa.beranger@ics.ci");
		employeBoa.setFonction("Directeur Production");
		employeBoa.setGenre(Genre.MASCULIN);
		employeBoa.setEstSuperieureHierachique(false);
		employeBoa.setEstChauffeur(false);
		employeBoa.setTelephoneEmploye("0601010101");
		employeBoa.setDirection(directionDSI);
		employeBoa.setSite(siteProduction);
		employeBoa.setStatutChauffeur(StatutChauffeur.DISPONIBLE);

		employeService.create(employeBoa);*/


		//Employe employeKoudioRosine2 = employeService.findById(22L);

		//String p = "123";
		//String password = EncrytedPasswordUtils.encrytePassword(p);

		//System.out.println("===============DEBUT TRANSACTION=======================");
		//UserRepository userRepository = ctx.getBean(UserRepository.class);

		/*AppUser rosine1 = userRepository.save(new AppUser("rosine", password, true, employeKoudioRosine2));
		//AppUser cedric = userRepository.save(new AppUser("cedric", password, true, employeGoubo1));
		System.out.println("===============AJOUT CLIENT=======================");
		userRepository.findAll().forEach(u->System.out.println(u.getUserName()));
		System.out.println("Utilisateurs ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");*/

		/*AppUser rosine3 = userRepository.getById(13L);

		AppRole rolemoyengeneraux4 = roleRepository.findByRoleId(6L);
		System.out.println("===============AFFECTATION ROLE PAR USER=======================");
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		userRoleRepository.save(new UserRole(rosine3,rolemoyengeneraux4));


		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&FIN DE LA TRANSACTION&&&&&&&&&&&&&&&&&&&");*/
		/*UserRole userRole = userRoleRepository.getById(22L);
		userRoleRepository.delete(userRole);*/



		String server ="Server start on http://localhost:8089";
		System.out.println(server);

        /*EmployeService employeService = ctx.getBean(EmployeService.class);
        SiteService siteService = ctx.getBean(SiteService.class);
        *//*DirectionService directionService = ctx.getBean(DirectionService.class);
        DmdService dmdService = ctx.getBean(DmdService.class);*//*
        EmployeDmdService employeDmdService = ctx.getBean(EmployeDmdService.class);

        Direction directionPRODUCTION =directionService.findById(2L);
        Direction directionTECHNIQUE =directionService.findById(3L);
        Direction directionDSI =directionService.findById(1L);
        Site siteProduction = siteService.findById(2L);
        Site sitePerso = siteService.findById(1L);

        Employe employeMamadou = new Employe();
        employeMamadou.setNumMatEmpl(101L);
        employeMamadou.setNom("COULIBALY".toUpperCase());
        employeMamadou.setPrenom("MAMADOU".toUpperCase());
        employeMamadou.setDateEmbauche(LocalDate.of(2000,12, 12));
        employeMamadou.setDateNaissance(LocalDate.of(1980,1, 1));
        employeMamadou.setEmail("mamadou.coulibaly@ics.ci");
        employeMamadou.setFonction("Informaticien");
        employeMamadou.setGenre(Genre.MASCULIN);
        employeMamadou.setEstSuperieureHierachique(false);
        employeMamadou.setEstChauffeur(false);
        employeMamadou.setTelephoneEmploye("0301010101");
        employeMamadou.setDirection(directionDSI);
        employeMamadou.setSite(siteProduction);
        Employe employeNkagou1 = employeService.create(employeMamadou);
*/

    }

}

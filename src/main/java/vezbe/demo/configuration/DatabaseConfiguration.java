package vezbe.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vezbe.demo.model.*;
import vezbe.demo.repository.*;
import java.util.Date;
import java.util.*;

@Configuration
public class DatabaseConfiguration {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MenadzerRepository menadzerRepository;

    @Autowired
    private DostavljacRepository dostavljacRepository;

    @Autowired
    private KupacRepository kupacRepository;

    @Autowired
    private TipKupcaRepository tipKupcaRepository;

    @Autowired
    private LokacijaRepository lokacijaRepository;

    @Autowired
    private RestoranRepository restoranRepository;

    @Autowired
    private ArtikalRepository artikalRepository;

    @Autowired
    private ArtikalPorudzbinaRepository artikalPorudzbinaRepository;

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;

    @Autowired
    private KomentarRepository komentarRepository;

    @Bean
    public boolean instantiate(){

        Lokacija lokacija1 = new Lokacija(32.51,21.42,"Vojvodjanska 35");
        Lokacija lokacija2 = new Lokacija(12.23,11.32,"Partizanska 95");
        Lokacija lokacija3 = new Lokacija(39.11,5.96,"Banatska 11");

        lokacijaRepository.save(lokacija1);
        lokacijaRepository.save(lokacija2);
        lokacijaRepository.save(lokacija3);

        Artikal artikal1 = new Artikal("Kapricosa", 1300, Artikal.Tip.jelo, 800, "Kecap, sampinjoni, sunka i kackavalj");
        Artikal artikal2 = new Artikal("Bolonjeze", 650, Artikal.Tip.jelo, 500, "Paradajz, meso, spagete");
        Artikal artikal3 = new Artikal("Voda", 100, Artikal.Tip.pice, 250, "Voda za pice");
        Artikal artikal4 = new Artikal("Limunada", 150, Artikal.Tip.pice, 250, "Limun i voda");
        Artikal artikal5 = new Artikal("Kapucino", 200, Artikal.Tip.pice, 200, "Kafa");
        Artikal artikal6 = new Artikal("Guarana", 200, Artikal.Tip.pice, 250, "Energetsko pice");
        Artikal artikal7 = new Artikal("Giros", 400, Artikal.Tip.jelo, 500, "Meso, tortilja, prilozi");
        Artikal artikal8 = new Artikal("Pljeskavica", 250, Artikal.Tip.jelo, 300, "Puno luka");
        Artikal artikal9 = new Artikal("Kifla", 150, Artikal.Tip.jelo, 100, "Pecivo");
        Artikal artikal10 = new Artikal("Jogurt", 50, Artikal.Tip.pice, 500, "Mlecni proizvod");

        artikalRepository.saveAll(
                List.of(artikal1, artikal2, artikal3, artikal4, artikal5, artikal6, artikal7, artikal8, artikal9, artikal10)
        );

        Set<Artikal> artikli1 = new HashSet<>();
        artikli1.add(artikal1);
        artikli1.add(artikal2);
        artikli1.add(artikal3);
        artikli1.add(artikal4);

        Restoran restoran1 = new Restoran("Italiano", "Italijanski", artikli1, lokacija1);
        restoranRepository.save(restoran1);


        Set<Artikal> artikli2 = new HashSet<>();
        artikli2.add(artikal9);
        artikli2.add(artikal10);
        artikli2.add(artikal3);
        artikli2.add(artikal4);

        Restoran restoran2 = new Restoran("Žeki", "Pekara", artikli2, lokacija2);
        restoranRepository.save(restoran2);

        Set<Artikal> artikli3 = new HashSet<>();
        artikli3.add(artikal5);
        artikli3.add(artikal6);
        artikli3.add(artikal7);
        artikli3.add(artikal8);

        Restoran restoran3 = new Restoran("Jefta", "Brza hrana", artikli3, lokacija3);
        restoranRepository.save(restoran3);

        Date date1 = new Date(29, 01, 10); // datum nam ne prikazuje kako treba
        Date date2 = new Date(1980-1900,05,11);
        Date date3 = new Date(1972-1900,10,05);
        Menadzer menadzer1 = new Menadzer("peraperic", "peraperic123", "Pera", "Peric", Korisnik.Pol.MUSKO, date1, restoran1, Korisnik.Uloga.Menadzer);
        Menadzer menadzer2 = new Menadzer("mikamikic", "mikamikic123", "Mika", "Mikic", Korisnik.Pol.MUSKO, date2, restoran2, Korisnik.Uloga.Menadzer);
        Menadzer menadzer3 = new Menadzer("zikazikic", "zikazikic123", "Zika", "Zikic", Korisnik.Pol.MUSKO, date3, restoran3, Korisnik.Uloga.Menadzer);
        menadzerRepository.saveAll(
                List.of(menadzer1, menadzer2, menadzer3)
        );

        TipKupca tip1 = new TipKupca(TipKupca.Tip.Zlatni, 0.5, 500);
        TipKupca tip2 = new TipKupca(TipKupca.Tip.Srebrni, 0.2, 200);
        tipKupcaRepository.save(tip1);
        tipKupcaRepository.save(tip2);

        Date date4 = new Date(2022-1900, 04,12,21,05,03);
        Date date5 = new Date(2022-1900, 04,12,21,10,03);
        Date date6 = new Date(2022-1900, 04,12,21,15,03);

        Kupac kupac1 = new Kupac("ivanaivanic", "ivanaivanic123", "Ivana", "Ivanic", Korisnik.Pol.ZENSKO, date1, Korisnik.Uloga.Kupac, null, 200, tip2);
        Kupac kupac2 = new Kupac("jovajovic", "jovajovic123", "Jova", "Jovic", Korisnik.Pol.MUSKO, date2, Korisnik.Uloga.Kupac, null, 500, tip1);
        kupacRepository.save(kupac1);
        kupacRepository.save(kupac2);

        Porudzbina porudzbina1 = new Porudzbina(restoran1, kupac1, Porudzbina.Status.ceka_dostavljaca);
        Porudzbina porudzbina2 = new Porudzbina(restoran2, kupac1, Porudzbina.Status.u_pripremi);
        porudzbinaRepository.save(porudzbina1);
        porudzbinaRepository.save(porudzbina2);

        ArtikalPorudzbina ap1 = new ArtikalPorudzbina(artikal1, porudzbina1, 3);
        ArtikalPorudzbina ap4 = new ArtikalPorudzbina(artikal7, porudzbina1, 9); // dodao
        artikalPorudzbinaRepository.save(ap1);
        artikalPorudzbinaRepository.save(ap4);
        porudzbina1.setArtikli(Set.of(ap1, ap4));
        porudzbina1.setCena(artikal1.getCena()*ap1.getKolicina()+artikal7.getCena()* ap4.getKolicina());
        porudzbina1.setDatum_vreme(date4);
        porudzbinaRepository.save(porudzbina1); // naknadno dodao

        ArtikalPorudzbina ap2 = new ArtikalPorudzbina(artikal9, porudzbina2, 4);
        artikalPorudzbinaRepository.save(ap2);
        porudzbina2.setArtikli(Set.of(ap2));
        porudzbina2.setCena(artikal9.getCena()*ap2.getKolicina());
        porudzbina2.setDatum_vreme(date5);
        porudzbinaRepository.save(porudzbina2); // naknadno dodao

        Porudzbina porudzbina3 = new Porudzbina(restoran3, kupac2, Porudzbina.Status.ceka_dostavljaca);
        porudzbinaRepository.save(porudzbina3);

        ArtikalPorudzbina ap3 = new ArtikalPorudzbina(artikal6, porudzbina3, 6);
        artikalPorudzbinaRepository.save(ap3);
        porudzbina3.setArtikli(Set.of(ap3));
        porudzbina3.setCena(artikal6.getCena()*ap3.getKolicina());
        porudzbina3.setDatum_vreme(date6);
        porudzbinaRepository.save(porudzbina3); // naknadno dodao

        Set<Porudzbina> istorija1 = new HashSet<>();
        istorija1.add(porudzbina1);
        istorija1.add(porudzbina2);
        kupac1.setIstorija_porudzbina(istorija1);
        kupacRepository.save(kupac1);   // naknadno dodao
        Set<Porudzbina> istorija2 = new HashSet<>();
        istorija2.add(porudzbina3);
        kupac2.setIstorija_porudzbina(istorija2);
        kupacRepository.save(kupac2); // naknadno dodao


        Dostavljac dostavljac1 = new Dostavljac("markomaric", "markomaric123", "Marko", "Maric", Korisnik.Pol.MUSKO, date3, Korisnik.Uloga.Dostavljac, istorija1);
        dostavljacRepository.save(dostavljac1);

        Admin admin1 = new Admin("jelajelic", "jelajelic123", "Jela", "Jelic", Korisnik.Pol.ZENSKO, date2, Korisnik.Uloga.Admin);
        adminRepository.save(admin1);

        Komentar komentar1 = new Komentar(kupac1, restoran1, "Ekstra hrana!", 5);
        Komentar komentar2 = new Komentar(kupac2, restoran2, "Okej je.", 3);
        komentarRepository.save(komentar1);
        komentarRepository.save(komentar2);

        return true;
    }
}
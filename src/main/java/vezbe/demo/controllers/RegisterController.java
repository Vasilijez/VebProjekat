package vezbe.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.dto.RegisterDto;
import vezbe.demo.model.Kupac;
import vezbe.demo.service.KupacService;
import vezbe.demo.service.RegisterService;
import vezbe.demo.service.SessionService;

import java.util.Date;
import java.util.HashMap;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private KupacService kupacService;

    @PostMapping("api/register")
    public ResponseEntity Register(@RequestBody RegisterDto registerDto){
        HashMap<String, String> errorDic = Validate(registerDto);

        if (!errorDic.isEmpty()){
            return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);
        }

        Kupac kupac = registerDto.ToKupac();
        kupacService.postaviTip(kupac); // Dodao sam ovde inicijalni tip kupca, jer me je zezalo kada sam pokusao da dodam u RegisterDto

        try{
            registerService.Register(kupac, "Kupac");
        } catch (Exception e){
            errorDic.put("Korisnicko ime", e.getMessage());
        }

        if (!errorDic.isEmpty())
            return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);

        return new ResponseEntity("Ok", HttpStatus.OK);
    }

    private HashMap<String, String> Validate(RegisterDto registerDto){ // Dodao validacije za sva polja
        HashMap<String, String> errorDic = new HashMap<>();

        if (registerDto.getIme() == null || registerDto.getIme().isEmpty() || registerDto.getIme().length()>25)
            errorDic.put("Ime", "Polje ime je neispravno uneseno");
        if (registerDto.getPrezime() == null || registerDto.getPrezime().isEmpty() || registerDto.getPrezime().length()>25)
            errorDic.put("Prezime", "Polje prezime je neispravno uneseno");
        if (registerDto.getKorisnickoIme() == null || registerDto.getKorisnickoIme().isEmpty() || registerDto.getKorisnickoIme().length()>25)
            errorDic.put("Korisnicko ime", "Polje korisnicko ime je neispravno uneseno");
        if (registerDto.getLozinka() == null || registerDto.getLozinka().isEmpty() || registerDto.getLozinka().length()>26)
            errorDic.put("Lozinka", "Polje lozinka je neispravno uneseno");
        if (registerDto.getDatum_rodjenja() == null || registerDto.getDatum_rodjenja().after(new Date()) || (registerDto.getDatum_rodjenja().before(new Date(1900-1900, 01,01))))
            errorDic.put("Datum", "Polje datum je neispravno uneseno");
        if (registerDto.getPol() == null)
            errorDic.put("Datum", "Polje pol je neispravno uneseno");

        return errorDic;
    }
}

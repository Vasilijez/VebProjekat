package vezbe.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.dto.RegisterDto;
import vezbe.demo.model.Kupac;
import vezbe.demo.service.RegisterService;
import vezbe.demo.service.SessionService;

import java.util.HashMap;

@RestController
public class RegisterController {


    @Autowired
    private RegisterService registerService;
    @Autowired
    private SessionService sessionService;

    @PostMapping("api/register")
    public ResponseEntity Register(@RequestBody RegisterDto registerDto){
        HashMap<String, String> errorDic = Validate(registerDto);

        if (!errorDic.isEmpty()){
            return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);
        }

        Kupac kupac = registerDto.ToKupac();

        try{
            registerService.Register(kupac, "Kupac");
        } catch (Exception e){
            errorDic.put("Koriscko ime", e.getMessage());
        }

        if (!errorDic.isEmpty())
            return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);

        return new ResponseEntity("Ok", HttpStatus.OK);
    }

    private HashMap<String, String> Validate(RegisterDto registerDto){
        HashMap<String, String> errorDic = new HashMap<>();

        if (registerDto.getIme() == null || registerDto.getIme().isEmpty())
            errorDic.put("Ime", "Ime je obavezno");
        if (registerDto.getPrezime() == null || registerDto.getPrezime().isEmpty())
            errorDic.put("Prezime", "Prezime je obavezno");
        if (registerDto.getKorisnicko_ime() == null || registerDto.getKorisnicko_ime().isEmpty())
            errorDic.put("Korisnicko ime", "Korisnicko ime je obavezno");
        if (registerDto.getLozinka() == null || registerDto.getLozinka().isEmpty())
            errorDic.put("Lozinka", "Lozinka je obaveznas");

        return errorDic;
    }
}

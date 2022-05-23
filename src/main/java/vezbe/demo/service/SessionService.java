package vezbe.demo.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SessionService {


    public boolean validateUloga(HttpSession session, String NazivUloge){
        Object uloga= session.getAttribute("uloga");
        if (uloga == null) return false;
        if (!uloga.toString().endsWith(NazivUloge)) return false;
        return true;
    }

    public String getKorisnicko_Ime(HttpSession session){
        Object korisnicko_Ime= session.getAttribute("korisnicko_ime");
        if (korisnicko_Ime == null) return "";
        if (korisnicko_Ime.toString().isEmpty()) return "";
        return korisnicko_Ime.toString();
    }

    public String getUloga(HttpSession session){
        Object Uloga = session.getAttribute("uloga");
        if (Uloga == null) return "";
        if (Uloga.toString().isEmpty()) return "";
        return Uloga.toString();
    }

    public boolean validateSession(HttpSession session){
        if (session == null)
            return false;

        String korisnicko_ime = getKorisnicko_Ime(session);
        String uloga = getUloga(session);

        if (korisnicko_ime == null || korisnicko_ime.isEmpty())
            return false;
        if (uloga == null || uloga.isEmpty())
            return false;
        return true;
    }
}

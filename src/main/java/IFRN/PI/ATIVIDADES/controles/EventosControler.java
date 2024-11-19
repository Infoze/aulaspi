package IFRN.PI.ATIVIDADES.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import IFRN.PI.ATIVIDADES.molds.Evento;
import IFRN.PI.ATIVIDADES.repositories.ConvidadoRepository;
import IFRN.PI.ATIVIDADES.repositories.EventoRepository;

@Controller
@RequestMapping("/eventos")
public class EventosControler {

	@Autowired
	private EventoRepository er;
	private IFRN.PI.ATIVIDADES.molds.convidado convidado;
	@Autowired
	private ConvidadoRepository cr;
	private List<IFRN.PI.ATIVIDADES.molds.convidado> all;

	@GetMapping("/form")
	public String form() {
		return "eventos/formEvento";

	}

	@PostMapping
	public String adicionar(Evento evento) {

		System.out.println(evento);
		er.save(evento);

		return "eventos/evento-adicionado";
	}

	@GetMapping
	public ModelAndView listar() {
		List<Evento> eventos = er.findAll();
		ModelAndView mv = new ModelAndView("eventos/lista");
		mv.addObject("eventos", eventos);
		return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView detalhar(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Evento> opt = er.findById(id);
		if (opt.isEmpty()) {
			md.setViewName("redirect:/eventos");
			return md;
		}
		md.setViewName("eventos/detalhes");
		Evento evento = opt.get();
		md.addObject("evento", evento);
		
		List<IFRN.PI.ATIVIDADES.molds.convidado> convidados = cr.findByEvento(evento);
		md.addObject("convidados", convidados);
		
		return md;

	}

	@PostMapping("/{idEventos}")
	public String salvarConvidado(@PathVariable Long idEventos, IFRN.PI.ATIVIDADES.molds.convidado convidado) {
		System.out.println("Id do evento" + idEventos);
		System.out.println(convidado);

		Optional<Evento> opt = er.findById(idEventos);
		if (opt.isEmpty()) {
			return "redirect:/eventos";

		}
		
		Evento evento = opt.get();
		convidado.setEvento(evento);
		
		cr.save(convidado);

		return "redirect:/eventos/{idEventos}";
	}

}

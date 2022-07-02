import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import EmployeesView from '../views/EmployeesView.vue'
import RestoraniView from '../views/RestoraniView.vue'
import RestoranView from '../views/RestoranView.vue'
import EmployeeView from '../views/EmployeeView.vue'
import AddEmployeeView from '../views/AddEmployeeView.vue'
import EditEmployeeView from '../views/EditEmployeeView.vue'
import LoginView from '../views/LoginView.vue'
import AdminView from '../views/AdminView.vue'
import MenadzerView from '../views/MenadzerView.vue'
import RegisterView from '../views/RegisterView.vue'
import ProfilView from '../views/ProfilView.vue'
import KreirajMenadzeraView from '../views/KreirajMenadzeraView.vue'
import KreirajDostavljacaView from '../views/KreirajDostavljacaView.vue'
import KreirajRestoranView from '../views/KreirajRestoranView.vue'
import ObrisiRestoranView from '../views/ObrisiRestoranView.vue'
import IzaberiMenadzeraView from '../views/IzaberiMenadzeraView.vue'


const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/izaberi-menadzera',
    name: 'IzaberiMenadzera',
    component: IzaberiMenadzeraView
  },
  {
    path: '/obrisi-restoran',
    name: 'ObrisiRestoran',
    component: ObrisiRestoranView
  },
  {
    path: '/kreiraj-restoran',
    name: 'KreirajRestoran',
    component: KreirajRestoranView
  },
  {
    path: '/kreiraj-dostavljaca',
    name: 'KreirajDostavljaca',
    component: KreirajDostavljacaView
  },
  {
    path: '/kreiraj-menadzera',
    name: 'KreirajMenadzera',
    component: KreirajMenadzeraView
  },
  {
    path: '/profil',
    name: 'Profil',
    component: ProfilView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/menadzer',
    name: 'menadzer',
    component: MenadzerView
  },
  {
    path: '/restorani',
    name: 'restorani',
    component: RestoraniView
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminView
  },
  {
    path: '/restoran',
    name: 'restoran',
    component: RestoranView
  },
  {
    path: '/employees',
    name: 'employees',
    component: EmployeesView
  },
  {
    path: '/employee',
    name: 'employee',
    component: EmployeeView
  },
  {
    path: '/add-employee',
    name: 'add-employee',
    component: AddEmployeeView
  },
  {
    path: '/edit-employee',
    name: 'edit-employee',
    component: EditEmployeeView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

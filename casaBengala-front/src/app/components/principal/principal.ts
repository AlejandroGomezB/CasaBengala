import { Component, signal, computed, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit, PLATFORM_ID, Inject } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import AOS from 'aos';
import { RegistraService } from '../../service/registar/registra-service';
import { RegistroInterface } from '../../interface/registro-interface';
import {email, form, FormField, required} from '@angular/forms/signals';

@Component({
  selector: 'app-principal',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule,FormField],
  templateUrl: './principal.html',
  styleUrl: './principal.css',
})
export class Principal implements OnInit, OnDestroy, AfterViewInit {

  isBrowser: boolean = false;
  error:boolean = false
  private countdownInterval?: number;
  private registerInterval?: number;

  time = { days: '00', hours: '00', minutes: '00', seconds: '00' };
  visible = { evento: false, registro: false };
  timeLeft = signal(300);
  minutes = computed(() => String(Math.floor(this.timeLeft() / 60)).padStart(2, '0'));
  seconds = computed(() => String(this.timeLeft() % 60).padStart(2, '0'));

  images: string[] = Array.from({ length: 12 }, (_, i) => `assets/img/galeria/${i + 1}.jpg`);
  form = { nombre: '', email: '', pase: '' };
  message: string = '';
  mostrarMensaje = signal(true);
  recordModel = signal<RegistroInterface>({
    nombre: '',
    apellidos: '',
    email: '',
    ciudad: '',
    edad: 0
  });

  recordForm = form(this.recordModel, (schemaPath) => {
    required(schemaPath.nombre, {message: 'Nombre es requerido'});
    required(schemaPath.apellidos, {message: 'Apelldios es requerido'});
    required(schemaPath.ciudad, {message: 'Ciudad es requerido'});
    required(schemaPath.edad, {message: 'Edad es requerido'});
    required(schemaPath.email, {message: 'Correo electronico es requerido'});
    email(schemaPath.email, {message: 'Ingresa un correo electronico correcto'});
  });

  constructor(
    @Inject(PLATFORM_ID) platformId: object,
    private service: RegistraService
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  ngOnInit(): void {
    this.updateCountdown();
    this.timeLeft = signal(300);
    if (this.isBrowser) {
      this.countdownInterval = window.setInterval(() => this.updateCountdown(), 1000);
      this.registerInterval = window.setInterval(() => {
        if (this.timeLeft() > 0) this.timeLeft.update(v => v - 1);
      }, 1000);
    }
  }

  ngAfterViewInit(): void {
    if (this.isBrowser) {
      AOS.init({
        duration: 2000,
        once: true,
        mirror: true,
        offset: 120
      });

      AOS.refresh();
    }
  }

  ngOnDestroy(): void {
    if (this.countdownInterval) clearInterval(this.countdownInterval);
    if (this.registerInterval) clearInterval(this.registerInterval);
  }

  private updateCountdown(): void {
    const end = new Date('2026-06-20T09:00:00').getTime();
    const diff = Math.max(0, end - Date.now());
    this.time.days = String(Math.floor(diff / 86400000)).padStart(2, '0');
    this.time.hours = String(Math.floor((diff % 86400000) / 3600000)).padStart(2, '0');
    this.time.minutes = String(Math.floor((diff % 3600000) / 60000)).padStart(2, '0');
    this.time.seconds = String(Math.floor((diff % 60000) / 1000)).padStart(2, '0');
  }

  onSubmit(event: Event): void {
    event.preventDefault();
    this.mostrarMensaje.set(true);
    if (this.recordForm().invalid()) return;

    this.service.postRecord(this.recordModel()).subscribe({
      error: (data: any) => {
          this.error = true
          this.message = data.error.message
          console.log(data)
          this.ocultarMensaje();
        },
      next: (data: any) => {
        this.error = false
        this.message = 'Se registro correctamente'
        this.recordModel.set({
          nombre: '',
          apellidos: '',
          email: '',
          ciudad: '',
          edad: 0
        });
        this.recordForm().reset();
        this.ocultarMensaje();
      }
    })

  }

  ocultarMensaje(): void {
    setTimeout(() => {
        this.mostrarMensaje.set(false);
        this.message = '';
      }, 10000);
  }

}

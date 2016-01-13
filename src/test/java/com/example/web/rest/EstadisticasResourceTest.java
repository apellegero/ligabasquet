package com.example.web.rest;

import com.example.Application;
import com.example.domain.Estadisticas;
import com.example.repository.EstadisticasRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EstadisticasResource REST controller.
 *
 * @see EstadisticasResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EstadisticasResourceTest {


    private static final Integer DEFAULT_CANASTES = 1;
    private static final Integer UPDATED_CANASTES = 2;

    private static final Integer DEFAULT_ASISTENCIES = 1;
    private static final Integer UPDATED_ASISTENCIES = 2;

    private static final Integer DEFAULT_REBOTES = 1;
    private static final Integer UPDATED_REBOTES = 2;

    private static final Integer DEFAULT_FALTAS = 1;
    private static final Integer UPDATED_FALTAS = 2;

    private static final Integer DEFAULT_PUNTOS = 1;
    private static final Integer UPDATED_PUNTOS = 2;

    @Inject
    private EstadisticasRepository estadisticasRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restEstadisticasMockMvc;

    private Estadisticas estadisticas;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EstadisticasResource estadisticasResource = new EstadisticasResource();
        ReflectionTestUtils.setField(estadisticasResource, "estadisticasRepository", estadisticasRepository);
        this.restEstadisticasMockMvc = MockMvcBuilders.standaloneSetup(estadisticasResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        estadisticas = new Estadisticas();
        estadisticas.setCanastes(DEFAULT_CANASTES);
        estadisticas.setAsistencies(DEFAULT_ASISTENCIES);
        estadisticas.setRebotes(DEFAULT_REBOTES);
        estadisticas.setFaltas(DEFAULT_FALTAS);
        estadisticas.setPuntos(DEFAULT_PUNTOS);
    }

    @Test
    @Transactional
    public void createEstadisticas() throws Exception {
        int databaseSizeBeforeCreate = estadisticasRepository.findAll().size();

        // Create the Estadisticas

        restEstadisticasMockMvc.perform(post("/api/estadisticass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estadisticas)))
                .andExpect(status().isCreated());

        // Validate the Estadisticas in the database
        List<Estadisticas> estadisticass = estadisticasRepository.findAll();
        assertThat(estadisticass).hasSize(databaseSizeBeforeCreate + 1);
        Estadisticas testEstadisticas = estadisticass.get(estadisticass.size() - 1);
        assertThat(testEstadisticas.getCanastes()).isEqualTo(DEFAULT_CANASTES);
        assertThat(testEstadisticas.getAsistencies()).isEqualTo(DEFAULT_ASISTENCIES);
        assertThat(testEstadisticas.getRebotes()).isEqualTo(DEFAULT_REBOTES);
        assertThat(testEstadisticas.getFaltas()).isEqualTo(DEFAULT_FALTAS);
        assertThat(testEstadisticas.getPuntos()).isEqualTo(DEFAULT_PUNTOS);
    }

    @Test
    @Transactional
    public void getAllEstadisticass() throws Exception {
        // Initialize the database
        estadisticasRepository.saveAndFlush(estadisticas);

        // Get all the estadisticass
        restEstadisticasMockMvc.perform(get("/api/estadisticass"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(estadisticas.getId().intValue())))
                .andExpect(jsonPath("$.[*].canastes").value(hasItem(DEFAULT_CANASTES)))
                .andExpect(jsonPath("$.[*].asistencies").value(hasItem(DEFAULT_ASISTENCIES)))
                .andExpect(jsonPath("$.[*].rebotes").value(hasItem(DEFAULT_REBOTES)))
                .andExpect(jsonPath("$.[*].faltas").value(hasItem(DEFAULT_FALTAS)))
                .andExpect(jsonPath("$.[*].puntos").value(hasItem(DEFAULT_PUNTOS)));
    }

    @Test
    @Transactional
    public void getEstadisticas() throws Exception {
        // Initialize the database
        estadisticasRepository.saveAndFlush(estadisticas);

        // Get the estadisticas
        restEstadisticasMockMvc.perform(get("/api/estadisticass/{id}", estadisticas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(estadisticas.getId().intValue()))
            .andExpect(jsonPath("$.canastes").value(DEFAULT_CANASTES))
            .andExpect(jsonPath("$.asistencies").value(DEFAULT_ASISTENCIES))
            .andExpect(jsonPath("$.rebotes").value(DEFAULT_REBOTES))
            .andExpect(jsonPath("$.faltas").value(DEFAULT_FALTAS))
            .andExpect(jsonPath("$.puntos").value(DEFAULT_PUNTOS));
    }

    @Test
    @Transactional
    public void getNonExistingEstadisticas() throws Exception {
        // Get the estadisticas
        restEstadisticasMockMvc.perform(get("/api/estadisticass/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstadisticas() throws Exception {
        // Initialize the database
        estadisticasRepository.saveAndFlush(estadisticas);

		int databaseSizeBeforeUpdate = estadisticasRepository.findAll().size();

        // Update the estadisticas
        estadisticas.setCanastes(UPDATED_CANASTES);
        estadisticas.setAsistencies(UPDATED_ASISTENCIES);
        estadisticas.setRebotes(UPDATED_REBOTES);
        estadisticas.setFaltas(UPDATED_FALTAS);
        estadisticas.setPuntos(UPDATED_PUNTOS);
        

        restEstadisticasMockMvc.perform(put("/api/estadisticass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estadisticas)))
                .andExpect(status().isOk());

        // Validate the Estadisticas in the database
        List<Estadisticas> estadisticass = estadisticasRepository.findAll();
        assertThat(estadisticass).hasSize(databaseSizeBeforeUpdate);
        Estadisticas testEstadisticas = estadisticass.get(estadisticass.size() - 1);
        assertThat(testEstadisticas.getCanastes()).isEqualTo(UPDATED_CANASTES);
        assertThat(testEstadisticas.getAsistencies()).isEqualTo(UPDATED_ASISTENCIES);
        assertThat(testEstadisticas.getRebotes()).isEqualTo(UPDATED_REBOTES);
        assertThat(testEstadisticas.getFaltas()).isEqualTo(UPDATED_FALTAS);
        assertThat(testEstadisticas.getPuntos()).isEqualTo(UPDATED_PUNTOS);
    }

    @Test
    @Transactional
    public void deleteEstadisticas() throws Exception {
        // Initialize the database
        estadisticasRepository.saveAndFlush(estadisticas);

		int databaseSizeBeforeDelete = estadisticasRepository.findAll().size();

        // Get the estadisticas
        restEstadisticasMockMvc.perform(delete("/api/estadisticass/{id}", estadisticas.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Estadisticas> estadisticass = estadisticasRepository.findAll();
        assertThat(estadisticass).hasSize(databaseSizeBeforeDelete - 1);
    }
}

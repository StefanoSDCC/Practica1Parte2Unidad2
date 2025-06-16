import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, NumberField, TextField, VerticalLayout,HorizontalLayout,Icon, Select, } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { ArtistaService, CancionService, TaskService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import Cancion from 'Frontend/generated/org/unl/music/base/models/Cancion';
import { useCallback, useEffect, useState } from 'react';

export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Cancion',
  },
};


type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};

type CancionEntryFormPropsUpdate = ()=> {
  onCancionUpdated?: () => void;
};
//GUARDARTISTA
function CancionEntryForm(props: CancionEntryFormProps) {
  const nombre = useSignal('');
  const genero = useSignal('');
  const album = useSignal('');
  const duracion = useSignal('');
  const url = useSignal('');
  const tipo = useSignal('');
  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && genero.value.trim().length > 0) {
        const id_genero = parseInt(genero.value) +1;
        const id_album = parseInt(album.value) +1;
        await CancionService.create(nombre.value, id_genero, parseInt(duracion.value), url.value, tipo.value, id_album);
        if (props.onCancionCreated) {
          props.onCancionCreated();
        }

        nombre.value = '';
        genero.value = '';
        album.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumGenero().then(data =>
      //console.log(data)
      listaGenero.value = data
    );
  }, []);
  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumCombo().then(data =>
      //console.log(data)
      listaAlbum.value = data
    );
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipo().then(data =>
      //console.log(data)
      listaTipo.value = data
    );
  }, []);
  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Nueva Cancion"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Cancelar
            </Button>
            <Button onClick={createCancion} theme="primary">
              Registrar
            </Button>

          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre del cancion"
            placeholder="Ingrese el nombre de la cancion"
            aria-label="Nombre del cancion"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <ComboBox label="Genero"
            items={listaGenero.value}
            placeholder='Seleccione un genero'
            aria-label='Seleccione un genero de la lista'
            value={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
            />
            <ComboBox label="Album"
            items={listaAlbum.value}
            placeholder='Seleccione un album'
            aria-label='Seleccione un album de la lista'
            value={album.value}
            onValueChanged={(evt) => (album.value = evt.detail.value)}
            />
            <ComboBox label="Tipo"
            items={listaTipo.value}
            placeholder='Seleccione un tipo de archivo'
            aria-label='Seleccione un tipo de archivo de la lista'
            value={tipo.value}
            onValueChanged={(evt) => (tipo.value = evt.detail.value)}
            />
            <NumberField  label="Duracion"
            placeholder="Ingrese la Duracion de la cancion"
            aria-label="Nombre la Duracion de la cancion"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
          />
          <TextField label="Direccion de la cancion"
            placeholder="Ingrese la direccion fisica o digital de la cancion"
            aria-label="Nombre de la direccion de la cancion"
            value={url.value}
            onValueChanged={(evt) => (url.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button
            onClick={() => {
              dialogOpened.value = true;
            }}
          >
            Añadir Cancion
          </Button>
    </>
  );
}

//GUARDARTISTA
const CancionEntryFormUpdate = function(props: CancionEntryFormPropsUpdate){//useCallback((props: ArtistaEntryFormPropsUpdate,{ item: art }: { item: Artista }) => {
  const dialogOpened = useSignal(false);

    const listaGenero = useSignal<String[]>([]);
    const listaAlbum = useSignal<String[]>([]);
    const listaTipo = useSignal<String[]>([]);

    const nombre = useSignal(props.arguments.nombre);
    const genero = useSignal(props.arguments.genero);
    const album = useSignal(props.arguments.album);
    const duracion = useSignal(props.arguments.duracion.toString());
    const url = useSignal(props.arguments.url);
    const tipo = useSignal(props.arguments.tipo);
    useEffect(() => {
        CancionService.listaAlbumGenero().then(data => listaGenero.value = data);
        CancionService.listaAlbumCombo().then(data => listaAlbum.value = data);
        CancionService.listTipo().then(data => listaTipo.value = data);
      }, []);
  const updateCancion = async () => {
      try {
        if (nombre.value.trim().length > 0 && genero.value.trim().length > 0) {
          const id_genero = parseInt(genero.value) + 1;
          const id_album = parseInt(album.value) + 1;
        await CancionService.update(props.arguments.id,nombre.value,id_genero,parseInt(duracion.value),url.value,tipo.value,id_album);
        if (props.arguments.onCancionUpdated) {
            props.arguments.onCancionUpdated();
        }

        nombre.value = '';
        genero.value = '';
        album.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };


  return (
    <>
      <Dialog
        modeless
        headerTitle="Actualizar Cancion"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Cancelar
            </Button>
            <Button onClick={updateCancion} theme="primary">
              Registrar
            </Button>

          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '10rem', maxWidth: '100%' }}>
          <TextField label="Nombre de la Cancion"
            placeholder="Ingrese el nombre de la Cancion"
            aria-label="Nombre de la cancion"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />

          <ComboBox label="genero"
            items={listaGenero.value}
            placeholder='Seleccione un genero'
            aria-label='Seleccione un genero de la lista'
            value={genero.value}
            defaultValue={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
            />

            <ComboBox
            label="Album"
            items={listaAlbum.value}
            placeholder="Seleccione un álbum"
            value={album.value}
            onValueChanged={(evt) => (album.value = evt.detail.value)}
            />
            <ComboBox
            label="Tipo"
            items={listaTipo.value}
            placeholder="Seleccione un tipo"
            value={tipo.value}
            onValueChanged={(evt) => (tipo.value = evt.detail.value)}
            />

            <NumberField  label="Duracion"
            placeholder="Ingrese la Duracion de la cancion"
            aria-label="Nombre la Duracion de la cancion"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
            />
            <TextField
            label="URL"
            value={url.value}
            onValueChanged={(evt) => (url.value = evt.detail.value)}
            />
        </VerticalLayout>
      </Dialog>
      <Button
            onClick={() => {
              dialogOpened.value = true;
            }}
          >
            Editar
          </Button>
    </>
  );
};


//LISTARTISTAS
export default function CancionView() {

//   const dataProvider = useDataProvider<Cancion>({
//     list: () => CancionService.listCancion(),
//   });

const [items, setItems] = useState([]);
  useEffect(() => {
    CancionService.listCancion().then(function (data) {
      //items.values = data;
      setItems(data);
    });
  }, []);


const order = (event, columnId) => {
    console.log(event);
    const direction = event.detail.value;
    // Custom logic based on the sorting direction
    console.log(`Sort direction changed for column ${columnId} to ${direction}`);

    var dir = (direction == 'asc') ? 1 : 2;
    CancionService.order(columnId, dir).then(function (data) {
      setItems(data);
    });
  }

  //BUSQUEDABINARIA
  const criterio = useSignal('');
  const texto = useSignal('');
  const itemSelect = [
    {
      label: 'Cancion',
      value: 'nombre',
    },
    {
      label: 'Album',
      value: 'album',
    },
    {
        label: 'Genero',
        value: 'genero',
    },
    {
        label: 'URL',
        value: 'url',
    },
    {
        label: 'Duracion',
        value: 'duracion',
    },
    {
      label: 'Tipo',
      value: 'tipo',
    },

  ];
  const search = async () => {
    try {
      console.log(criterio.value+" "+texto.value);
      CancionService.search(criterio.value, texto.value, 0).then(function (data) {
        setItems(data);
      });

      criterio.value = '';
      texto.value = '';

      Notification.show('Busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });


    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

const searchBynary = async () => {
    try {
      console.log(criterio.value+" "+texto.value);
      CancionService.searchBinaryLineal(criterio.value, texto.value, 1).then(function (data) {
        setItems(data);
      });

      criterio.value = '';
      texto.value = '';

      Notification.show('Busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });


    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };


  function indexLink({ item}: { item: Cancion }) {

    return (
      <span>
        <CancionEntryFormUpdate arguments={item} onCancionUpdated={items.refresh}>

          </CancionEntryFormUpdate>
      </span>
    );
  }

  function indexIndex({model}:{model:GridItemModel<Cancion>}) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }

  function searchLineal(event: MouseEvent<Button, MouseEvent>): void {
    throw new Error('Function not implemented.');
  }

  return (

    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Canciones" />

      <div style={{ display: 'flex', alignItems: 'center', marginBottom: '2rem', width: '100%' }}>
        <div style={{ marginRight: '2rem' }}>
          <CancionEntryForm onCancionCreated={items.refresh} />
        </div>
        <div style={{ display: 'flex', alignItems: 'center', width: '100%' }}>
          <Select
            items={itemSelect}
            value={criterio.value}
            onValueChanged={evt => (criterio.value = evt.detail.value)}
            placeholder="Seleccione el criterio"
            style={{ minWidth: '220px', marginLeft: 0 }}
          />
          <TextField
            placeholder="Texto a buscar"
            style={{ width: '50%', minWidth: '250px', marginLeft: '1rem' }}
            value={texto.value}
            onValueChanged={evt => (texto.value = evt.detail.value)}
          >
            <Icon slot="prefix" icon="vaadin:search" />
          </TextField>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem', marginLeft: '2rem' }}>
            <Button onClick={search} theme="primary">
              Busqueda B
            </Button>
            <Button onClick={searchLineal} theme="primary">
              Busqueda LB
            </Button>
          </div>
        </div>
      </div>

      <Grid items={items}>
        <GridSortColumn path="nombre" header="Nombre" onDirectionChanged={(e) => order(e, "nombre")} />
        <GridSortColumn path="genero" header="Genero" onDirectionChanged={(e) => order(e, "genero")} />
        <GridSortColumn path="album" header="Album" onDirectionChanged={(e) => order(e, "album")} />
        <GridSortColumn path="tipo" header="Tipo" onDirectionChanged={(e) => order(e, "tipo")} />
        <GridSortColumn path="duracion" header="Duracion" onDirectionChanged={(e) => order(e, "duracion")} />
        <GridSortColumn path="url" header="URL" onDirectionChanged={(e) => order(e, "url")}  />
        <GridColumn header="Acciones" renderer={indexLink} />
      </Grid>
    </main>
  );
}

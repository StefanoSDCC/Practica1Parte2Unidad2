import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import {Button,ComboBox,Dialog,Grid,GridColumn,GridItemModel,TextField,VerticalLayout}
from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { GeneroService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import Genero from 'Frontend/generated/org/unl/music/base/models/Genero';
import { useEffect } from 'react';
import { updateGenero } from 'Frontend/generated/GeneroService';

export const config: ViewConfig = {
  title: 'Genero',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Generos',
  },
};


type GeneroEntryFormProps = {
  onGeneroCreated?: () => void;
};

type GeneroEntryFormUpdateProps = {
  genero: Genero;
  onGeneroUpdated?: () => void;
};
//GUARDAR ARTISTA
function GeneroEntryForm(props: GeneroEntryFormProps) {
  const nombre = useSignal('');

  const createGenero = async () => {
    try {
      if (nombre.value.trim().length > 0) {
        await GeneroService.createGenero(nombre.value);
        if (props.onGeneroCreated) {
          props.onGeneroCreated();
        }
        nombre.value = '';
        dialogOpened.value = false;
        Notification.show('Genero creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Nuevo Genero"
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
              Candelar
            <Button
              onClick={async () => {
                // You need to provide the correct arguments for updateGenero here
                // For example, if you have the id and nombre available:
                // await updateGenero(id, nombre);
                // Otherwise, remove this button or implement the logic as needed.
                Notification.show('Funcionalidad de actualizar no implementada', { duration: 3000, position: 'bottom-end', theme: 'error' });
              }}
              theme="primary"
            >
              Actualizar
            </Button>
            </Button>

          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre del Genero"
            placeholder="Ingrese el nombre del Genero"
            aria-label="Nombre del Genero"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />

        </VerticalLayout>
      </Dialog>
      <Button
            onClick={() => {
              dialogOpened.value = true;
            }}
          >
            Agregar
          </Button>
    </>
  );
}

const GeneroEntryFormUpdate = function(props: GeneroEntryFormUpdateProps) {
  const nombre = useSignal(props.genero?.nombre ?? '');

  const updateGenero = async () => {
    try {
      if (nombre.value.trim().length > 0) {
        await GeneroService.updateGenero(props.genero.id, nombre.value);
        if (props.onGeneroUpdated) {
          props.onGeneroUpdated();
        }
        nombre.value = '';
        dialogOpened.value = false;
        Notification.show('Genero actualizado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Actualizar Genero"
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
              Candelar
            </Button>
            <Button
              onClick={updateGenero}
              theme="primary"
            >
              Actualizar
            </Button>
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Nombre del Genero"
            placeholder="Ingrese el nombre del Genero"
            aria-label="Nombre del Genero"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
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


//LISTA DE ARTISTAS
export default function GeneroView() {

  const dataProvider = useDataProvider<Genero>({
    list: async () => {
      const result = await GeneroService.listAll();
      return (result ?? []).filter((g): g is Genero => g !== undefined);
    },
  });

  function indexLink({ item }: { item: Genero }) {
    return (
      <span>
        <GeneroEntryFormUpdate genero={item} onGeneroUpdated={dataProvider.refresh} />
      </span>
    );
  }

  function indexIndex({model}:{model:GridItemModel<Genero>}) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }

  return (

    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Lista de Genero">
        <Group>
          <GeneroEntryForm onGeneroCreated={dataProvider.refresh}/>
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn path="nombre" header="Nombre del Genero" />
        <GridColumn header="Acciones" renderer={indexLink}/>
      </Grid>
    </main>
  );
}
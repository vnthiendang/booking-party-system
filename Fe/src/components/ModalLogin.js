import * as React from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { Stack } from "@mui/material";

import TextField from "@mui/material/TextField";

import Link from "@mui/material/Link";
import LoadingButton from "@mui/lab/LoadingButton";

import { Card } from "@mui/material";
import { useForm, Controller } from "react-hook-form";
import { Bounce, toast } from "react-toastify";
import { AuthApi } from "../api";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 600,
  bgcolor: "background.paper",
  //   border: "2px solid #000",
  boxShadow: 24,
  //   p: 4,
};

export default function ModalLogin({
  open,
  handleClose,

  handleOpenConfirmModal,
}) {
  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm({
    mode: "onSubmit",
    defaultValues: {
      email: "",
      password: "",
    },
  });
  async function onSubmit(data) {
    if (!data.email || !data.password) {
      toast.error("Email or password is required", {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        transition: Bounce,
      });
    }
    try {
      const res = await AuthApi.login({
        email: data.email,
        password: data.password,
      });
      sessionStorage.setItem("token", JSON.stringify(res.access_token));
      sessionStorage.setItem("info", JSON.stringify(res));
      toast.success("🦄 Login success!", {
        position: "top-center",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        transition: Bounce,
      });
      handleClose();
      handleOpenConfirmModal();
    } catch (error) {
      toast.error("🦄 Something went wrong!", {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        transition: Bounce,
      });
    }
  }
  async function onError(error) {
    console.log("🚀 ~ onError ~ errors:", errors);
  }

  const renderForm = (
    <>
      <Stack spacing={3}>
        <Controller
          name="email"
          control={control}
          render={({ field }) => (
            <TextField type="email" label="Email address" {...field} />
          )}
        />
        <Controller
          name="password"
          control={control}
          render={({ field }) => (
            <TextField type="password" label="Password" {...field} />
          )}
        />
      </Stack>

      <Stack
        direction="row"
        alignItems="center"
        justifyContent="flex-end"
        sx={{ my: 3 }}
      >
        <Link variant="subtitle2" underline="hover">
          Forgot password?
        </Link>
      </Stack>

      <LoadingButton
        fullWidth
        size="large"
        type="submit"
        variant="contained"
        color="inherit"
      >
        Login
      </LoadingButton>
    </>
  );

  return (
    <div>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Box
            sx={{
              height: 1,
              mt: 10,
            }}
          >
            <Stack
              alignItems="center"
              justifyContent="center"
              sx={{ height: 1 }}
            >
              <Card
                sx={{
                  p: 5,
                  width: 1,
                  maxWidth: 420,
                  border: "10px solid #fefefe",
                }}
              >
                <Typography
                  variant="h4"
                  sx={{
                    marginBottom: "10px",
                  }}
                >
                  Sign in to Minimal
                </Typography>
                <form onSubmit={handleSubmit(onSubmit, onError)}>
                  {renderForm}
                </form>
              </Card>
            </Stack>
          </Box>
        </Box>
      </Modal>
    </div>
  );
}

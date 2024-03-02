import * as React from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { Button, Stack } from "@mui/material";
import { giftImg } from "../asset";
import { useState } from "react";
import Avatar from "@mui/material/Avatar";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import LoadingButton from "@mui/lab/LoadingButton";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Container from "@mui/material/Container";
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

export default function ModalRegister({
  open,
  handleClose,
  handleOpenLogiModal,
}) {
  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm({
    mode: "onSubmit",
    defaultValues: {},
  });

  async function onSubmit(data) {
    if (!data.email || !data.password || !data.phone || !data.displayName) {
      toast.error("Field is required", {
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
      await AuthApi.register({
        email: data.email,
        password: data.password,
        displayName: data.displayName,
        phone: data.phone,
      });

      toast.success("🦄 register success, please Login before booking!", {
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
      handleClose();
      handleOpenLogiModal();
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
                  maxWidth: 550,
                  border: "10px solid #fefefe",
                }}
              >
                <Container component="main" maxWidth="xs">
                  <CssBaseline />
                  <div>
                    <Stack spacing={2}>
                      <Avatar
                        display
                        alignItems="center"
                        style={{ backgroundColor: "pink" }}
                      >
                        <LockOutlinedIcon />
                      </Avatar>
                      <Typography component="h1" variant="h4">
                        Sign up
                      </Typography>
                    </Stack>
                    <form onSubmit={handleSubmit(onSubmit, onError)}>
                      <Grid container spacing={5}>
                        <Grid item xs={12} sm={6}>
                          <Controller
                            name="displayName"
                            control={control}
                            render={({ field }) => (
                              <TextField
                                variant="outlined"
                                {...field}
                                fullWidth
                                label="Display Name"
                                name="displayName"
                              />
                            )}
                          />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                          <Controller
                            name="phone"
                            control={control}
                            render={({ field }) => (
                              <TextField
                                variant="outlined"
                                {...field}
                                fullWidth
                                label="Phone"
                                name="phone"
                              />
                            )}
                          />
                        </Grid>
                        <Grid item xs={12}>
                          <Controller
                            name="email"
                            control={control}
                            render={({ field }) => (
                              <TextField
                                variant="outlined"
                                {...field}
                                fullWidth
                                label="Email Address"
                                name="email"
                              />
                            )}
                          />
                        </Grid>
                        <Grid item xs={12}>
                          <Controller
                            name="password"
                            control={control}
                            render={({ field }) => (
                              <TextField
                                variant="outlined"
                                {...field}
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                              />
                            )}
                          />
                        </Grid>

                        {/* <Grid item xs={12}>
                          <FormControlLabel
                            control={
                              <Checkbox
                                value="allowExtraEmails"
                                color="primary"
                              />
                            }
                            label="I accept to the term of services"
                          />
                        </Grid> */}
                      </Grid>
                      <LoadingButton
                        type="submit"
                        size="large"
                        color="inherit"
                        fullWidth
                        variant="contained"
                      >
                        Sign Up
                      </LoadingButton>
                      {/* <Grid container justify="flex-end">
                        <Grid item>
                          <Link href="auth" variant="body2">
                            Already have an account? Sign in
                          </Link>
                        </Grid>
                      </Grid> */}
                    </form>
                  </div>
                  <Box mt={5}></Box>
                </Container>
              </Card>
            </Stack>
          </Box>
        </Box>
      </Modal>
    </div>
  );
}

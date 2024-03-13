import { useState } from "react";
import PropTypes from "prop-types";

import Stack from "@mui/material/Stack";
import Avatar from "@mui/material/Avatar";
import Popover from "@mui/material/Popover";
import TableRow from "@mui/material/TableRow";
import Checkbox from "@mui/material/Checkbox";
import MenuItem from "@mui/material/MenuItem";
import TableCell from "@mui/material/TableCell";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";

import Label from "../../components/label";
import Iconify from "../../components/iconify";
import { Bounce, toast } from "react-toastify";
import { HostApi } from "../../api";
import ModalCancelBooking from "../../components/ModalCancelBooking";

// ----------------------------------------------------------------------

export default function UserTableRow({
  selected,
  name,
  avatarUrl,
  company,
  role,
  isVerified,
  status,
  totalCost,
  pService,
  deposited,
  item,
  handleClick,
  gelistPackage,
}) {
  const [open, setOpen] = useState(null);
  const [openModalCancel, setOpenCancel] = useState(false);

  const handleOpenMenu = (event) => {
    setOpen(event.currentTarget);
  };

  const handleCloseMenu = () => {
    setOpen(null);
  };

  const handleApprove = async () => {
    console.log(item);
    try {
      await HostApi.updateBookingStatus(item?.bookingId, {
        userId: item?.customerUsId,
        packageId: item?.packageId,
        status: "APPROVED",
      });
      toast.success("🦄 approve success!", {
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
      gelistPackage();
      handleCloseMenu();
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
  };
  const handleDone = async () => {
    try {
      await HostApi.updateBookingStatus(item?.bookingId, {
        userId: item?.customerUsId,
        packageId: item?.packageId,
        status: "COMPLETED",
      });
      toast.success("🦄 Booking COMPLETED!", {
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
      gelistPackage();
      handleCloseMenu();
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
  };
  const handleCancel = async () => {
    try {
      await HostApi.cancelBooking(item?.bookingId);
      toast.success("🦄 Booking Cancel!", {
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
      gelistPackage();
      setOpenCancel(false);
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
  };

  return (
    <>
      <TableRow hover tabIndex={-1} role="checkbox" selected={selected}>
        {/* <TableCell padding="checkbox">
          <Checkbox disableRipple checked={selected} onChange={handleClick} />
        </TableCell> */}

        <TableCell component="th" scope="row" padding="none">
          <Stack direction="row" alignItems="center" spacing={2}>
            <Typography variant="subtitle2" noWrap>
              {name}
            </Typography>
          </Stack>
        </TableCell>

        <TableCell>{company}</TableCell>

        <TableCell>
          {pService?.length > 0
            ? pService?.map((item) => (
                <>
                  {item?.serviceName} - ${item?.price}
                  <br />
                </>
              ))
            : "-"}
        </TableCell>

        <TableCell align="center">{isVerified}</TableCell>

        <TableCell>{status}</TableCell>

        <TableCell>{totalCost?.toLocaleString()}</TableCell>

        <TableCell>{deposited?.toLocaleString() || "-"}</TableCell>
        <TableCell>
          {isVerified === "COMPLETED" ? totalCost?.toLocaleString() : 0}
        </TableCell>

        <TableCell>
          {isVerified === "REFUNDED" ? item?.refundMoney?.toLocaleString() : 0}
        </TableCell>

        <TableCell align="right">
          {!["COMPLETED", "CANCELLED", "REFUNDED"].includes(isVerified) && (
            <IconButton onClick={handleOpenMenu}>
              <Iconify icon="eva:more-vertical-fill" />
            </IconButton>
          )}
        </TableCell>
      </TableRow>

      <Popover
        open={!!open}
        anchorEl={open}
        onClose={handleCloseMenu}
        anchorOrigin={{ vertical: "top", horizontal: "left" }}
        transformOrigin={{ vertical: "top", horizontal: "right" }}
        PaperProps={{
          sx: { width: 140 },
        }}
      >
        {/* {["PENDING", "APPROVED",].includes(isVerified) && (
       
        )} */}
        <MenuItem
          onClick={() => {
            isVerified === "APPROVED" ? handleDone() : handleApprove();
          }}
        >
          <Iconify icon="eva:edit-fill" sx={{ mr: 2 }} />
          {isVerified === "APPROVED" ? "DONE" : "Approve"}
        </MenuItem>

        {!["COMPLETED", "CANCELLED", "REFUNDED"].includes(isVerified) && (
          <MenuItem
            onClick={() => setOpenCancel(true)}
            sx={{ color: "error.main" }}
          >
            <Iconify icon="eva:trash-2-outline" sx={{ mr: 2 }} />
            Cancel
          </MenuItem>
        )}
      </Popover>
      {openModalCancel && (
        <ModalCancelBooking
          open={openModalCancel}
          money={item?.deposited || 0}
          handleClose={() => setOpenCancel(false)}
          handleConfirm={handleCancel}
        />
      )}
    </>
  );
}

UserTableRow.propTypes = {
  avatarUrl: PropTypes.any,
  company: PropTypes.any,
  handleClick: PropTypes.func,
  isVerified: PropTypes.any,
  name: PropTypes.any,
  role: PropTypes.any,
  selected: PropTypes.any,
  status: PropTypes.string,
};
